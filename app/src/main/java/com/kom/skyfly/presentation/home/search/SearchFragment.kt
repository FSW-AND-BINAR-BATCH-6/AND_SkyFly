package com.kom.skyfly.presentation.home.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.skyfly.R
import com.kom.skyfly.databinding.FragmentSearchBinding
import com.kom.skyfly.presentation.home.search.viewitems.Items
import com.kom.skyfly.presentation.main.MainViewModel
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
        val maxPeekHeight =
            resources.getDimensionPixelSize(
                R.dimen.max_bottom_sheet_height,
            )
        setupBinding()
        getAirportData()
        setBottomSheetMaxHeight(maxPeekHeight)
        // Extract names from the mockList
    }

    private fun getAirportData() {
        searchViewModel.getAllAirports().observe(viewLifecycleOwner) { response ->
            response.proceedWhen(
                doOnSuccess = { result ->
                    result.payload?.let { sectionedSearch ->
                        // Clear the adapter to avoid duplicates
                        adapter.clear()
                        val items =
                            sectionedSearch.map { data ->
                                Items(data) { item ->
                                    mainViewModel.setDestination(item)
                                    dismiss()
                                }
                            }

                        adapter.addAll(items)
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

    private fun setBottomSheetMaxHeight(maxPeekHeight: Int) {
        dialog?.setOnShowListener {
            val bottomSheetDialog = it as? BottomSheetDialog
            val bottomSheet = bottomSheetDialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
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
    }
}
