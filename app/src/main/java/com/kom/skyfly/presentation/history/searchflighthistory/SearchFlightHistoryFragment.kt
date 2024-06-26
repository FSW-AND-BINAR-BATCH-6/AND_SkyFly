package com.kom.skyfly.presentation.history.searchflighthistory

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.skyfly.R
import com.kom.skyfly.data.datasource.history.FlightCodeListener
import com.kom.skyfly.data.model.searchhistory.SearchHistory
import com.kom.skyfly.databinding.FragmentSearchFlightHistoryBinding
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.presentation.history.searchflighthistory.adapter.SearchHistoryListAdapter
import com.kom.skyfly.presentation.history.searchflighthistory.adapter.SearchHistoryListener
import com.kom.skyfly.utils.hideKeyboard
import com.kom.skyfly.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFlightHistoryFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSearchFlightHistoryBinding

    private val searchFlightHistoryViewModel: SearchFlightHistoryViewModel by viewModel()
    private var flightCode: String? = null
    private var flightCodeListener: FlightCodeListener? = null
    private val adapter: SearchHistoryListAdapter by lazy {
        SearchHistoryListAdapter(
            itemClick = { searchHistory ->
                // Handle item click here if needed
            },
            searchHistoryListener =
                object : SearchHistoryListener {
                    override fun onDeleteItemClicked(searchHistory: SearchHistory) {
                        searchFlightHistoryViewModel.deleteSearchHistory(searchHistory)
                    }
                },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchFlightHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        getSearchHistoryData()
        setList()

        val maxPeekHeight =
            resources.getDimensionPixelSize(
                R.dimen.max_bottom_sheet_height,
            )
        setBottomSheetMaxHeight(maxPeekHeight)
    }

    private fun getSearchHistoryData() {
        searchFlightHistoryViewModel.getAllSearchHistory().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.rvPage.isVisible = true
                    binding.csvSearchHistory.setState(ContentState.SUCCESS)
                    result.payload.let {
                        if (it != null) {
                            adapter.submitData(it)
                        }
                    }
                },
                doOnError = {
                    binding.csvSearchHistory.setState(ContentState.ERROR_GENERAL)
                },
                doOnEmpty = {
                    binding.rvPage.isVisible = false
                    binding.csvSearchHistory.isVisible = true
                    binding.csvSearchHistory.setState(
                        ContentState.EMPTY,
                        getString(R.string.text_empty_search_history),
                    )
                },
            )
        }
    }

    private fun setBottomSheetMaxHeight(maxHeight: Int) {
        dialog?.setOnShowListener {
            val bottomSheetDialog = it as? BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                val behavior = BottomSheetBehavior.from(sheet)
                behavior.peekHeight = maxHeight
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun setOnClickListener() {
        binding.layoutSearchBar.ivCloseSearch.setOnClickListener {
            dismiss()
        }
        binding.layoutSearchBar.etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event != null &&
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                val searchHistory = binding.layoutSearchBar.etSearch.text.toString()
                observeData("39483943tfeff", searchHistory)
                binding.layoutSearchBar.etSearch.clearFocus()
                hideKeyboard()
                flightCodeListener?.onFlightCodeEntered(flightCode!!)
                dismiss()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.tvDeleteRecentSearches.setOnClickListener {
            searchFlightHistoryViewModel.deleteAllSearchHistory()
            Toast.makeText(requireContext(), "deleteall", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setList() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvPage.layoutManager = layoutManager
        binding.rvPage.adapter = this@SearchFlightHistoryFragment.adapter
    }

    private fun observeData(
        userId: String,
        searchHistory: String,
    ) {
        searchFlightHistoryViewModel.insertSearchHistory(userId, searchHistory)
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
}
