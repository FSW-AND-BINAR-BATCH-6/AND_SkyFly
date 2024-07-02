package com.kom.skyfly.presentation.home.search_result

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kom.skyfly.R
import com.kom.skyfly.core.BaseActivity
import com.kom.skyfly.data.model.home.Calendar
import com.kom.skyfly.data.model.home.intent.SearchResultIntent
import com.kom.skyfly.databinding.ActivitySearchResultBinding
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.presentation.home.detail_home.DetailHomeActivity
import com.kom.skyfly.presentation.home.filter.FilterFragment
import com.kom.skyfly.presentation.home.search_result.view_items.CalendarAdapter
import com.kom.skyfly.presentation.home.search_result.view_items.Items
import com.kom.skyfly.utils.NoInternetException
import com.kom.skyfly.utils.ServerErrorException
import com.kom.skyfly.utils.UnAuthorizeException
import com.kom.skyfly.utils.proceedWhen
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class SearchResultActivity : BaseActivity() {
    private val binding: ActivitySearchResultBinding by lazy {
        ActivitySearchResultBinding.inflate(layoutInflater)
    }
    private val adapter: GroupieAdapter by lazy {
        GroupieAdapter()
    }
    private val calendarAdapter: CalendarAdapter by lazy {
        CalendarAdapter {
            calendarAdapter.setSelectedDate(it.date)
            filterTime = it.date
            getAllTicketData(it.date, searchResultViewModel.filterName.value)
        }
    }

    private val searchResultViewModel: SearchResultViewModel by viewModel()
    private var departureAirport: String? = null
    private var arrivalAirport: String? = null
    private var departureTime: String? = null
    private var filterTime: String? = null
    private var adultCount: Int? = null
    private var childrenCount: Int? = null
    private var babyCount: Int? = null
    private var totalPassenger: Int? = null
    private var seatClass: String? = null
    private var extraRoundTrip: Boolean? = null
    private var returnDate: String? = null
    private var flightId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        seatClass = intent.getStringExtra("EXTRA_SEAT_CLASS")
        departureAirport = intent.getStringExtra("EXTRA_DEPARTURE_AIRPORT")
        arrivalAirport = intent.getStringExtra("EXTRA_ARRIVAL_AIRPORT")
        departureTime = intent.getStringExtra("EXTRA_DEPARTURE_TIME")
        returnDate = intent.getStringExtra("EXTRA_RETURN_TIME")
        adultCount = intent.getIntExtra("EXTRA_ADULT_COUNT", 0)
        totalPassenger = intent.getIntExtra("EXTRA_TOTAL_PASSENGER", 0)
        childrenCount = intent.getIntExtra("EXTRA_CHILD_COUNT", 0)
        babyCount = intent.getIntExtra("EXTRA_BABY_COUNT", 0)
        extraRoundTrip = intent.getBooleanExtra("EXTRA_ROUND_TRIP", false)
        setOnClickListener()
        getCalendarData()
        setupBinding()
        setSwipeRefresh()
        getAllTicketData(departureTime!!)
        checkFilter()
    }

    private fun setSwipeRefresh() {
        binding.rlSearchResult.setOnRefreshListener {
            if (filterTime != null) {
                getAllTicketData(filterTime!!, searchResultViewModel.filterName.value)
            } else {
                getAllTicketData(departureTime!!, searchResultViewModel.filterName.value)
            }
        }
    }

    private fun checkFilter() {
        // Observe filter name
        searchResultViewModel.filterName.observe(this) { filter ->
            when {
                filter != null -> {
                    binding.tvFilterName.text = filter
                    filterTime?.let {
                        getAllTicketData(it, filter)
                    } ?: getAllTicketData(departureTime!!)
                }
                filterTime != null -> {
                    getAllTicketData(filterTime!!)
                }
                else -> {
                    getAllTicketData(departureTime!!)
                }
            }
        }
    }

    private fun getCalendarData() {
        val recyclerView = binding.rvDateSearchResult
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        val startDate = LocalDate.parse(departureTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val daysInMonth = startDate.month.length(startDate.isLeapYear)
        val startDay = startDate.dayOfMonth
        val remainingDaysInMonth = daysInMonth - startDay + 1

        val days = mutableListOf<Calendar>()

        if (remainingDaysInMonth in 1..5) {
            for (day in startDay..daysInMonth) {
                val date = startDate.withDayOfMonth(day)
                val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("id", "ID"))
                val formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                days.add(Calendar(dayOfWeek, formattedDate))
            }

            val daysNeededFromNextMonth = 30 - remainingDaysInMonth
            var nextMonthDate = startDate.plusMonths(1).withDayOfMonth(1)
            for (day in 1..daysNeededFromNextMonth) {
                val dayOfWeek =
                    nextMonthDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("id", "ID"))
                val formattedDate = nextMonthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                days.add(Calendar(dayOfWeek, formattedDate))
                nextMonthDate = nextMonthDate.plusDays(1)
            }
        } else {
            val firstDayOfMonth = startDate.withDayOfMonth(1)
            for (day in 1..daysInMonth) {
                val date = firstDayOfMonth.withDayOfMonth(day)
                val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("id", "ID"))
                val formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                days.add(Calendar(dayOfWeek, formattedDate))
            }
        }

        recyclerView.adapter = calendarAdapter
        calendarAdapter.submitData(
            days,
            startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        )

        // Scroll to the initial selected date position
        val initialPosition =
            calendarAdapter.findPositionOfDate(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        if (initialPosition != RecyclerView.NO_POSITION) {
            layoutManager.scrollToPositionWithOffset(initialPosition, 0)
        }
    }

    private fun getAllReturnTicketData(date: String) {
        searchResultViewModel.getReturnFlightTicket(
            page = 1,
            arrivalAirport = arrivalAirport!!,
            departureAirport = departureAirport!!,
            departureDate = date,
            seatClass = seatClass,
            adult = adultCount,
            children = childrenCount,
            baby = babyCount,
            returnDate = returnDate,
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
                                                Log.d("dari return ticket data", "$item")
                                                val searchResultIntent =
                                                    SearchResultIntent(
                                                        returnId = item?.id,
                                                        departureId = flightId,
                                                        seatClass = item?.seatClass,
                                                        adultCount = adultCount,
                                                        childCount = childrenCount,
                                                        babyCount = babyCount,
                                                        roundTrip = extraRoundTrip,
                                                    )
                                                val intent =
                                                    Intent(
                                                        this@SearchResultActivity,
                                                        DetailHomeActivity::class.java,
                                                    ).apply {
                                                        putExtra(
                                                            "EXTRA_SEARCH_RESULT",
                                                            searchResultIntent,
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
                    Log.e(
                        "SearchResultActivityReturn",
                        "Error fetching data: ${it.exception?.message}",
                    )
                },
                doOnLoading = {
                    Log.d("SearchResultActivity", "Loading data")
                },
            )
        }
    }

    private fun getAllTicketData(
        date: String,
        sort: String? = null,
    ) {
        searchResultViewModel.getAllFlightTicket(
            page = 1,
            arrivalAirport = arrivalAirport!!,
            departureAirport = departureAirport!!,
            departureDate = date,
            seatClass = seatClass,
            adult = adultCount,
            children = childrenCount,
            baby = babyCount,
            returnDate = returnDate,
            sort = sort,
        ).observe(this) { response ->
            response.proceedWhen(
                doOnSuccess = { result ->
                    binding.rlSearchResult.isRefreshing = false
                    val sectionedSearch = result.payload ?: emptyList()

                    // Handle visibility and delays
                    if (searchResultViewModel.filterName.value != null) {
                        binding.filterResult.isVisible = true
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.shimmerTicket.isVisible = false
                        binding.rvDateSearchResult.isVisible = true
                        binding.rvItemTicketsSearchResult.isVisible = true

                        // Map sectionedSearch data
                        val sections =
                            sectionedSearch.map { data ->
                                Section().apply {
                                    add(
                                        Items(data) { item ->
                                            if (extraRoundTrip == true) {
                                                flightId = item?.id
                                                getAllReturnTicketData(departureTime!!)
                                            } else {
                                                val searchResultIntent =
                                                    SearchResultIntent(
                                                        departureId = item?.id,
                                                        seatClass = item?.seatClass,
                                                        adultCount = adultCount,
                                                        childCount = childrenCount,
                                                        babyCount = babyCount,
                                                        roundTrip = extraRoundTrip,
                                                        returnId = "",
                                                    )
                                                val intent =
                                                    Intent(
                                                        this@SearchResultActivity,
                                                        DetailHomeActivity::class.java,
                                                    ).apply {
                                                        putExtra(
                                                            "EXTRA_SEARCH_RESULT",
                                                            searchResultIntent,
                                                        )
                                                    }
                                                startActivity(intent)
                                            }
                                        },
                                    )
                                }
                            }

                        // Clear the adapter and add new sections
                        adapter.clear()
                        adapter.update(sections)
                    }, 1000)
                    Log.d("SearchResultActivity", "Data fetched successfully")
                },
                doOnEmpty = {
                    adapter.clear()
                    binding.shimmerTicket.isVisible = false
                    binding.csvTicketResult.setState(
                        ContentState.EMPTY,
                        getString(R.string.text_no_tickets_for_selected_date),
                    )
                },
                doOnError = { error ->
                    adapter.clear()
                    binding.shimmerTicket.isVisible = false
                    when (error.exception) {
                        is NoInternetException -> {
                            binding.csvTicketResult.setState(
                                ContentState.ERROR_NETWORK,
                                getString(R.string.no_internet_connection),
                            )
                        }

                        is UnAuthorizeException -> {
                            errorHandler(error.exception)
                            binding.csvTicketResult.setState(
                                ContentState.ERROR_NETWORK_GENERAL,
                                getString(R.string.text_session_expired_please_login_again),
                            )
                        }

                        is ServerErrorException -> {
                            errorHandler(error.exception)
                            binding.csvTicketResult.setState(
                                ContentState.ERROR_NETWORK,
                                getString(R.string.text_server_error_please_try_again_later),
                                R.drawable.img_empty_data,
                            )
                        }

                        else -> {
                            binding.csvTicketResult.setState(ContentState.ERROR_GENERAL)
                        }
                    }
                    Log.e("SearchResultActivity", "Error: ${error.exception?.message}")
                },
                doOnLoading = {
                    binding.shimmerTicket.isVisible = true
                    binding.csvTicketResult.isVisible = false
                    binding.rvItemTicketsSearchResult.isVisible = false
                    binding.rvDateSearchResult.isVisible = true
                },
            )
        }
    }

    private fun setupBinding() {
        binding.headerFlightSearchResult.tvTitleHeader.text =
            getString(
                R.string.text_header_search_result,
                departureAirport,
                arrivalAirport,
                totalPassenger.toString(),
                seatClass,
            )
        binding.rvItemTicketsSearchResult.apply {
            layoutManager = LinearLayoutManager(this@SearchResultActivity)
            adapter = this@SearchResultActivity.adapter
        }
    }

    private fun setOnClickListener() {
        binding.headerFlightSearchResult.ivBack.setOnClickListener {
            finish()
        }
        binding.filterButton.setOnClickListener {
            val filterData = FilterFragment()
            filterData.show(supportFragmentManager, filterData.tag)
        }
        binding.ivCloseFilter.setOnClickListener {
            binding.filterResult.isVisible = false
            searchResultViewModel.setFilterName(null)
            getAllTicketData(filterTime!!)
        }
    }
}
