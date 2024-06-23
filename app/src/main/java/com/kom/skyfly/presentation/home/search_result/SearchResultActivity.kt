package com.kom.skyfly.presentation.home.search_result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kom.skyfly.data.model.home.Calendar
import com.kom.skyfly.databinding.ActivitySearchResultBinding
import com.kom.skyfly.presentation.home.detail_home.DetailHomeActivity
import com.kom.skyfly.presentation.home.search_result.view_items.CalendarAdapter
import com.kom.skyfly.presentation.home.search_result.view_items.Items
import com.kom.skyfly.utils.proceedWhen
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class SearchResultActivity : AppCompatActivity() {
    private val binding: ActivitySearchResultBinding by lazy {
        ActivitySearchResultBinding.inflate(layoutInflater)
    }
    private val adapter: GroupieAdapter by lazy {
        GroupieAdapter()
    }
    private val calendarAdapter: CalendarAdapter by lazy {
        CalendarAdapter {
            getAllTicketData(it.date)
            Toast.makeText(this, "${it.date}", Toast.LENGTH_SHORT).show()
        }
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
        getCalendarData()
        getAllTicketData(departureTime!!)
    }

    private fun getCalendarData() {
        val recyclerView = binding.rvDateSearchResult
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val dateTime = YearMonth.parse(departureTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        val daysInMonth = dateTime.lengthOfMonth()

        val days = mutableListOf<Calendar>()
        for (day in 1..daysInMonth) {
            val date = dateTime.atDay(day)
            val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("id", "ID"))
            val formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            days.add(Calendar(dayOfWeek, formattedDate))
        }
        recyclerView.adapter = calendarAdapter
        calendarAdapter.submitData(days)
    }

    private fun getAllTicketData(date: String) {
        searchResultViewModel.getAllFlightTicket(
            page = 1,
            arrivalAirport = arrivalAirport!!,
            departureAirport = departureAirport!!,
            departureDate = date,
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
                                                    Intent(
                                                        this@SearchResultActivity,
                                                        DetailHomeActivity::class.java,
                                                    ).apply {
                                                        putExtra("EXTRA_FLIGHT_ID", item?.id)
                                                        putExtra(
                                                            "EXTRA_FLIGHT_SEATCLASS",
                                                            item?.seatClass,
                                                        )
                                                    }
                                                startActivity(intent)
                                            }
                                        }
                                    addAll(data)
                                }
                            }
                        adapter.clear()
                        adapter.update(sections)
                    }
                },
                doOnEmpty = {
                    adapter.clear()
                },
                doOnError = {
                    Log.e("SearchResultActivity", "Error fetching data: ${it.exception?.message}")
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
