package com.kom.skyfly.presentation.home.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.skyfly.data.model.home.filter.Filter
import com.kom.skyfly.databinding.FragmentFilterBinding
import com.kom.skyfly.presentation.home.filter.adapter.FilterAdapter
import com.kom.skyfly.presentation.home.search_result.SearchResultViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentFilterBinding
    private val filterViewModel: FilterViewModel by viewModel()
    private val sharedViewModel: SearchResultViewModel by activityViewModel()
    private var filterName: String? = null
    private val adapter: FilterAdapter by lazy {
        FilterAdapter { item ->
            item.let {
                filterName = it.filterName
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding(filterViewModel.getFilterName())
        setOnClickListener()
    }

    private fun setupBinding(data: List<Filter>) {
        binding.rvFilterCategories.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = this@FilterFragment.adapter
        }
        adapter.submitData(data)
    }

    private fun setOnClickListener() {
        binding.ivHomeCloseFilter.setOnClickListener {
            dismiss()
        }
        binding.btnFilterSave.setOnClickListener {
            sharedViewModel.setFilterName(filterName)
            dismiss()
        }
    }
}
