package com.kom.skyfly.presentation.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kom.skyfly.R
import com.kom.skyfly.databinding.FragmentHistoryBinding
import com.kom.skyfly.presentation.history.filterflighthistory.CalendarView
import com.kom.skyfly.presentation.history.flightdetailhistory.FlightDetailHistoryActivity
import com.kom.skyfly.presentation.history.searchflighthistory.SearchFlightHistoryFragment
import com.kom.skyfly.presentation.history.viewitems.DataItem
import com.kom.skyfly.presentation.history.viewitems.HeaderItem
import com.kom.skyfly.utils.proceedWhen
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding

    private val adapter: GroupieAdapter by lazy {
        GroupieAdapter()
    }

    private val historyViewModel: HistoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        getHistoryData()
        setRecyclerView()
        setClickListener()
    }

    private fun getHistoryData() {
        historyViewModel.getHistoryData().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.shimmerHistory.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvPage.isVisible = false
                },
                doOnSuccess = { data ->
                    binding.layoutState.root.isVisible = false
                    binding.shimmerHistory.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvPage.isVisible = true

                    adapter.clear()

                    data.payload?.let { sectionedDate ->
                        sectionedDate.data.forEach { itemsHistoryDomain ->
                            val section =
                                Section().apply {
                                    setHeader(
                                        HeaderItem(itemsHistoryDomain.date) { date ->
                                            Toast.makeText(
                                                requireContext(),
                                                "Header Clicked: $date",
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                        },
                                    )

                                    val uniqueItemsMap = LinkedHashMap<String, DataItem>()
                                    val sortedTransactions =
                                        itemsHistoryDomain.transactions.sortedByDescending { it.id }

                                    sortedTransactions.forEach { transaction ->
                                        val dataItem =
                                            DataItem(transaction) { clickedData, transactionId ->
                                                navigateToDetail(transactionId)
                                            }

                                        val key = transaction.id
                                        if (!uniqueItemsMap.containsKey(key)) {
                                            uniqueItemsMap[key] = dataItem
                                        }
                                    }

                                    uniqueItemsMap.forEach {
                                        add(it.value)
                                    }
                                }
                            adapter.add(section)
                        }
                    }
                },
                doOnError = { error ->
                    Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT)
                        .show()
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.shimmerHistory.isVisible = false
                    binding.layoutState.ivError.isVisible = true
                    binding.layoutState.tvTitleError.isVisible = true
                    binding.layoutState.tvTitleError.text =
                        getString(R.string.text_history_data_empty)
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text =
                        getString(R.string.text_havent_made_a_booking)
                    binding.rvPage.isVisible = false
                },
            )
        }
    }

    private fun setRecyclerView() {
        binding.rvPage.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HistoryFragment.adapter
        }
    }

    private fun setClickListener() {
        binding.clFilter.setOnClickListener {
            val calendarView = CalendarView()
            calendarView.show(parentFragmentManager, calendarView.tag)
        }
        binding.layoutHeaderOrderHistory.ivSearch.setOnClickListener {
            val searchHistory = SearchFlightHistoryFragment()
            searchHistory.show(parentFragmentManager, searchHistory.tag)
        }
        binding.rvPage.setOnClickListener {
        }
    }

    private fun navigateToDetail(transactionId: String?) {
        val intent =
            Intent(requireContext(), FlightDetailHistoryActivity::class.java).apply {
                putExtra("EXTRAS_TRANSACTION_ID", transactionId)
            }
        startActivity(intent)
    }
}
