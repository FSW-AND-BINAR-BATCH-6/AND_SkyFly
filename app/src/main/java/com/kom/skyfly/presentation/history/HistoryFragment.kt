package com.kom.skyfly.presentation.history

import SearchFlightHistoryFragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kom.skyfly.databinding.FragmentHistoryBinding
import com.kom.skyfly.presentation.history.filterflighthistory.CalendarView
import com.kom.skyfly.presentation.history.flightdetailhistory.FlightDetailHistoryActivity
import com.kom.skyfly.presentation.history.viewitems.DataItem
import com.kom.skyfly.presentation.history.viewitems.HeaderItem
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding

    private val adapter: GroupieAdapter by lazy {
        GroupieAdapter()
    }

    private val viewModel: HistoryViewModel by viewModels()

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
        setData()
        setClickListener()
    }

    private fun setData() {
        binding.rvPage.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HistoryFragment.adapter
        }

        val section =
            viewModel.getHistoryData().map {
                val section = Section()
                section.setHeader(
                    HeaderItem(it.date) { data ->
                        Toast.makeText(
                            requireContext(),
                            "Header Clicked : $data",
                            Toast.LENGTH_SHORT,
                        )
                            .show()
                    },
                )
                val dataSection =
                    it.data.map { data ->
                        DataItem(data) { clickedData ->
                            navigateToFlightDetail(clickedData.id)
                            Toast.makeText(requireContext(), clickedData.id, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                section.addAll(dataSection)
                section
            }
        adapter.addAll(section)
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

    private fun navigateToFlightDetail(flightId: String) {
        val intent = Intent(requireContext(), FlightDetailHistoryActivity::class.java)
        intent.putExtra("flight_id", flightId)
        startActivity(intent)
    }
}
