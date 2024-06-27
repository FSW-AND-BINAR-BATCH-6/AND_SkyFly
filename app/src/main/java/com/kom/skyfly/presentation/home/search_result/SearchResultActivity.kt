package com.kom.skyfly.presentation.home.search_result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kom.skyfly.R
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
            calendarAdapter.setSelectedDate(it.date)
            getAllTicketData(it.date)
        }
    }

    private val searchResultViewModel: SearchResultViewModel by viewModel()
    private var departureAirport: String? = null
    private var arrivalAirport: String? = null
    private var departureTime: String? = null
    private var adultCount: Int? = null
    private var childrenCount: Int? = null
    private var babyCount: Int? = null
    private var seatClass: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        seatClass = intent.getStringExtra("EXTRA_SEAT_CLASS")
        departureAirport = intent.getStringExtra("EXTRA_DEPARTURE_AIRPORT")
        arrivalAirport = intent.getStringExtra("EXTRA_ARRIVAL_AIRPORT")
        departureTime = intent.getStringExtra("EXTRA_DEPARTURE_TIME")
        adultCount = intent.getIntExtra("EXTRA_ADULT_COUNT", 0)
        childrenCount = intent.getIntExtra("EXTRA_CHILD_COUNT", 0)
        babyCount = intent.getIntExtra("EXTRA_BABY_COUNT", 0)
        setOnClickListener()
        setupBinding()
        getCalendarData()
        getAllTicketData(departureTime!!)
    }

    private fun getCalendarData() {
        val recyclerView = binding.rvDateSearchResult
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

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
            seatClass = seatClass,
            adult = adultCount,
            children = childrenCount,
            baby = babyCount,
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
                                                        putExtra("EXTRA_ADULT_COUNT", adultCount)
                                                        putExtra("EXTRA_CHILD_COUNT", childrenCount)
                                                        putExtra("EXTRA_BABY_COUNT", babyCount)
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
        binding.headerFlightSearchResult.tvTitleHeader.text =
            getString(R.string.text_example_header_search_result)
        binding.rvItemTicketsSearchResult.apply {
            layoutManager = LinearLayoutManager(this@SearchResultActivity)
            adapter = this@SearchResultActivity.adapter
        }
    }

    private fun setOnClickListener() {
        binding.headerFlightSearchResult.ivBack.setOnClickListener {
            finish()
        }
    }
}
