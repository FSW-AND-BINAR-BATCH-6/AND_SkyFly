package com.kom.skyfly.presentation.home.search_result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kom.skyfly.databinding.ActivitySearchResultBinding
import com.kom.skyfly.presentation.home.detail_home.DetailHomeActivity
import com.kom.skyfly.presentation.home.search_result.view_items.Items
import com.kom.skyfly.utils.proceedWhen
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchResultActivity : AppCompatActivity() {
    private val binding: ActivitySearchResultBinding by lazy {
        ActivitySearchResultBinding.inflate(layoutInflater)
    }
    private val adapter: GroupieAdapter by lazy {
        GroupieAdapter()
    }

    private val searchResultViewModel: SearchResultViewModel by viewModel()
    private var departureAirport: String? = null
    private var arrivalAirport: String? = null
    private var departureTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        departureAirport = intent.getStringExtra("EXTRA_DEPARTURE_AIRPORT")
        arrivalAirport = intent.getStringExtra("EXTRA_ARRIVAL_AIRPORT")
        departureTime = intent.getStringExtra("EXTRA_DEPARTURE_TIME")
        setOnClickListener()
        setupBinding()
        getAllTicketData()
    }

    private fun getAllTicketData() {
        searchResultViewModel.getAllFlightTicket(
            page = 1,
            arrivalAirport = arrivalAirport!!,
            departureAirport = departureAirport!!,
            departureDate = departureTime!!,
            seatClass = "ECONOMY",
        ).observe(this) { response ->
            response.proceedWhen(
                doOnSuccess = {
                    Log.d("SearchResultActivity", "Data fetched successfully")
                    it.payload?.let { sectionedSearch ->
                        val sections =
                            sectionedSearch.map {
                                Section().apply {
                                    val data =
                                        sectionedSearch.map { data ->
                                            Items(data) { item ->
                                                val intent =
                                                    Intent(this@SearchResultActivity, DetailHomeActivity::class.java).apply {
                                                        putExtra("EXTRA_FLIGHT_ID", item.id)
                                                        putExtra("EXTRA_FLIGHT_SEATCLASS", item.seatClass)
                                                    }
                                                startActivity(intent)
                                            }
                                        }
                                    addAll(data)
                                }
                            }
                        adapter.update(sections)
                    }
                },
                doOnError = {
                    Log.e("SearchResultActivity", "Error fetching data: ${it.message}")
                },
                doOnLoading = {
                    Log.d("SearchResultActivity", "Loading data")
                },
            )
        }
    }

    private fun setupBinding() {
        binding.rvItemTicketsSearchResult.apply {
            layoutManager = LinearLayoutManager(this@SearchResultActivity)
            adapter = this@SearchResultActivity.adapter
        }
    }

    private fun setOnClickListener() {
        binding.headerFlightSearchResult.ivBackBtnSearchResult.setOnClickListener {
            finish()
        }
    }
}
