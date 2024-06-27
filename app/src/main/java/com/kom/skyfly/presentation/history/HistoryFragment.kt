package com.kom.skyfly.presentation.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kom.skyfly.R
import com.kom.skyfly.core.BaseActivity
import com.kom.skyfly.data.datasource.history.FlightCodeListener
import com.kom.skyfly.databinding.FragmentHistoryBinding
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.presentation.history.filterflighthistory.CalendarView
import com.kom.skyfly.presentation.history.flightdetailhistory.FlightDetailHistoryActivity
import com.kom.skyfly.presentation.history.searchflighthistory.SearchFlightHistoryFragment
import com.kom.skyfly.presentation.history.viewitems.DataItem
import com.kom.skyfly.presentation.history.viewitems.HeaderItem
import com.kom.skyfly.utils.NoInternetException
import com.kom.skyfly.utils.ServerErrorException
import com.kom.skyfly.utils.UnAuthorizeException
import com.kom.skyfly.utils.proceedWhen
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate

class HistoryFragment : Fragment(), FlightCodeListener, CalendarView.DateSelectedListener {
    private lateinit var binding: FragmentHistoryBinding
    private val adapter: GroupieAdapter by lazy { GroupieAdapter() }
    private val limit: Int = 5000
    private var startDate: String? = null
    private var endDate: String? = null

    private val homeViewModel: HistoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        setupRecyclerView()
        setupSearchButton()
        getHistoryData(limit, null, null, null)
    }

    private fun setupRecyclerView() {
        binding.rvPage.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPage.adapter = adapter
    }

    private fun setupSearchButton() {
        binding.layoutHeaderOrderHistory.ivSearch.setOnClickListener {
            showSearchFlightHistoryFragment()
        }
    }

    private fun showSearchFlightHistoryFragment() {
        val searchFlightHistoryFragment = SearchFlightHistoryFragment()
        searchFlightHistoryFragment.setFlightCodeListener(this)
        searchFlightHistoryFragment.show(parentFragmentManager, searchFlightHistoryFragment.tag)
    }

    override fun onFlightCodeEntered(flightCode: String) {
        getHistoryData(limit, startDate, endDate, flightCode)
    }

    private fun getHistoryData(
        limit: Int?,
        startDate: String?,
        endDate: String?,
        flightCode: String?,
    ) {
        homeViewModel.getHistoryData(limit, startDate, endDate, flightCode)
            .observe(viewLifecycleOwner) { result ->
                result.proceedWhen(
                    doOnLoading = {
                        binding.shimmerHistory.isVisible = true
                        binding.rvPage.isVisible = false
                        binding.csvHistory.isVisible = false
                    },
                    doOnSuccess = { data ->
                        binding.shimmerHistory.isVisible = false
                        binding.rvPage.isVisible = true
                        binding.csvHistory.setState(ContentState.SUCCESS)

                        if (result.payload?.data == null || result.payload.data.isEmpty()) {
                            binding.csvHistory.setState(
                                ContentState.EMPTY,
                                getString(R.string.text_history_data_empty),
                            )
                        }

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
                        binding.shimmerHistory.isVisible = false

                        if (error.exception is NoInternetException) {
                            binding.csvHistory.setState(
                                ContentState.ERROR_NETWORK,
                                getString(R.string.no_internet_connection),
                            )
                        } else if (error.exception is UnAuthorizeException) {
                            (activity as BaseActivity).errorHandler(error.exception)
                            binding.csvHistory.setState(
                                ContentState.ERROR_NETWORK_GENERAL,
                                getString(R.string.text_session_expired_please_login_again),
                            )
                        } else if (error.exception is ServerErrorException) {
                            (activity as BaseActivity).errorHandler(error.exception)
                            binding.csvHistory.setState(
                                ContentState.ERROR_NETWORK,
                                getString(R.string.text_server_error_please_try_again_later),
                                R.drawable.img_empty_data,
                            )
                        } else {
                            binding.csvHistory.setState(ContentState.ERROR_GENERAL)
                        }
                        Log.e("HistoryFragment", "Error: ${error.exception?.message}")
                    },
                    doOnEmpty = {
                        binding.shimmerHistory.isVisible = false
                        binding.csvHistory.setState(
                            ContentState.EMPTY,
                            getString(R.string.text_history_data_empty),
                        )
                    },
                )
            }
    }

    private fun setClickListener() {
        binding.clFilter.setOnClickListener {
            val calendarView = CalendarView()
            calendarView.setDateSelectedListener(this)
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

    override fun onDateSelected(
        startDate: LocalDate?,
        endDate: LocalDate?,
    ) {
        this.startDate = startDate?.toString()
        this.endDate = endDate?.toString()
        getHistoryData(limit, this.startDate, this.endDate, null)
    }
}
