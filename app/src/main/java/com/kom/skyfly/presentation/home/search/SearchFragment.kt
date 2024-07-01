package com.kom.skyfly.presentation.home.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.skyfly.R
import com.kom.skyfly.data.model.home.search_history.SearchDestinationHistory
import com.kom.skyfly.databinding.FragmentSearchBinding
import com.kom.skyfly.presentation.home.search.adapter.SearchDestinationHistoryAdapter
import com.kom.skyfly.presentation.home.search.adapter.SearchDestinationHistoryListener
import com.kom.skyfly.presentation.home.search.viewitems.Items
import com.kom.skyfly.presentation.main.MainViewModel
import com.kom.skyfly.utils.afterTextChanged
import com.kom.skyfly.utils.proceedWhen
import com.xwray.groupie.GroupieAdapter
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModel()
    private val mainViewModel: MainViewModel by activityViewModel()
    private val adapter: GroupieAdapter by lazy {
        GroupieAdapter()
    }
    private val searchHistoryAdapter: SearchDestinationHistoryAdapter by lazy {
        SearchDestinationHistoryAdapter(
            itemClick = { searchDestinationHistory ->
                binding.layoutHomeSearchBar.etHomeSearch.setText(searchDestinationHistory.searchDestinationHistory)
            },
            searchDestinationHistoryListener =
                object : SearchDestinationHistoryListener {
                    override fun onDeleteItemClicked(searchDestinationHistory: SearchDestinationHistory) {
                        searchViewModel.deleteSearchHistory(searchDestinationHistory)
                    }
                },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        setList()
        val maxPeekHeight =
            resources.getDimensionPixelSize(
                R.dimen.max_bottom_sheet_height,
            )
        setupBinding()
        getRecentSearchAirport()
        setBottomSheetMaxHeight(maxPeekHeight)
    }

    private fun getAirportData(city: String?) {
        searchViewModel.getAllAirports(city = city).observe(viewLifecycleOwner) { response ->
            response.proceedWhen(
                doOnSuccess = { result ->
                    result.payload?.let { sectionedSearch ->
                        // Clear the adapter to avoid duplicates
                        adapter.clear()
                        val items =
                            sectionedSearch.map { data ->
                                Items(data) { item ->
                                    mainViewModel.setDestination(item)
                                    observeData(item.city)
                                    dismiss()
                                }
                            }

                        adapter.addAll(items)
                    }
                },
            )
        }
    }

    private fun observeData(searchHistory: String) {
        searchViewModel.insertSearchHistory(searchHistory)
            .observe(viewLifecycleOwner) { result ->
                result.proceedWhen(
                    doOnSuccess = {
                        Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                    },
                    doOnError = {
                        Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                    },
                )
            }
    }

    private fun getRecentSearchAirport() {
        searchViewModel.getAllSearchHistory().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.tvHomeDeleteRecentSearches.isVisible = true
                    binding.tvTitleHomeRecentSearches.isVisible = true
                    binding.rvRecentDestinationSearch.isVisible = true
                    binding.rvHomeSearchPage.isVisible = false
                    result.payload?.let {
                        searchHistoryAdapter.submitData(it)
                    }
                },
            )
        }
    }

    private fun setupBinding() {
        binding.rvHomeSearchPage.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchFragment.adapter
        }
    }

    private fun setList() {
        binding.rvRecentDestinationSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecentDestinationSearch.adapter = searchHistoryAdapter
    }

    private fun setBottomSheetMaxHeight(maxPeekHeight: Int) {
        dialog?.setOnShowListener {
            val bottomSheetDialog = it as? BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                val behavior = BottomSheetBehavior.from(sheet)
                behavior.peekHeight = maxPeekHeight
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun setOnClickListener() {
        binding.layoutHomeSearchBar.ivCloseSearch.setOnClickListener {
            dismiss()
        }
        binding.tvHomeDeleteRecentSearches.setOnClickListener {
            searchViewModel.deleteAllSearchHistory()
        }
        binding.layoutHomeSearchBar.etHomeSearch.afterTextChanged {
            if (it.isNotEmpty()) {
                binding.rvHomeSearchPage.isVisible = true
                binding.rvRecentDestinationSearch.isVisible = false
                binding.tvHomeDeleteRecentSearches.isVisible = false
                binding.tvTitleHomeRecentSearches.isVisible = false
                getAirportData(it)
            } else {
                binding.tvTitleHomeRecentSearches.isVisible = true
                binding.rvHomeSearchPage.isVisible = false
                binding.rvRecentDestinationSearch.isVisible = true
                getRecentSearchAirport()
            }
        }
    }
}
