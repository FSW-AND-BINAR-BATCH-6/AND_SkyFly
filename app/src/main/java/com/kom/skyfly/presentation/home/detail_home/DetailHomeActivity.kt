package com.kom.skyfly.presentation.home.detail_home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kom.skyfly.R
import com.kom.skyfly.data.model.home.flight.FlightTicket
import com.kom.skyfly.data.model.home.flight_detail.FlightDetailTicket
import com.kom.skyfly.databinding.ActivityDetailHomeBinding
import com.kom.skyfly.presentation.checkout.bookersbiodata.BookersBiodataActivity
import com.kom.skyfly.presentation.home.search_result.view_items.Items
import com.kom.skyfly.utils.proceedWhen
import com.xwray.groupie.Group
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailHomeActivity : AppCompatActivity() {
    private val binding: ActivityDetailHomeBinding by lazy {
        ActivityDetailHomeBinding.inflate(layoutInflater)
    }
    private val adapter: GroupieAdapter by lazy {
        GroupieAdapter()
    }
    private val detailViewModel: DetailHomeViewModel by viewModel()
    private var extraRoundTrip: Boolean? = null
    private var extraId: String? = null
    private var extraSeatClass: String? = null
    private var adultCount: Int? = 0
    private var childCount: Int? = 0
    private var babyCount: Int? = 0
    private var roundtripTicket: ArrayList<FlightTicket>? = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        extraRoundTrip = intent.getBooleanExtra("EXTRA_ROUND_TRIP", false)
        extraId = intent.getStringExtra("EXTRA_FLIGHT_ID")
        extraSeatClass = intent.getStringExtra("EXTRA_FLIGHT_SEATCLASS")
        adultCount = intent.getIntExtra("EXTRA_ADULT_COUNT", 0)
        childCount = intent.getIntExtra("EXTRA_CHILD_COUNT", 0)
        babyCount = intent.getIntExtra("EXTRA_BABY_COUNT", 0)
        roundtripTicket = intent.getParcelableArrayListExtra("EXTRA_ROUNDTRIP_TICKET")
        setOnClickListener()
        observeData()
    }

    private fun observeData() {
        if (extraRoundTrip == true) {
            getTicketRoundTripData()
        } else {
            detailViewModel.getDetailTicketById(extraId!!, extraSeatClass)
                .observe(this@DetailHomeActivity) { result ->
                    result.proceedWhen(
                        doOnSuccess = {
                            it.payload?.let { flightDetail ->
                                Log.d("detailTicket", "$flightDetail")
                                setupBinding(flightDetail)
                            }
                        },
                        doOnError = {
                            Log.d("Error from Detail", "${it.message}")
                        },
                    )
                }
        }
    }

    private fun getTicketRoundTripData() {
        roundtripTicket?.let { sectionedSearch ->
            val sections =
                sectionedSearch.map {
                    Section().apply {
                        val data =
                            sectionedSearch.map { data ->
                                Items(data) { item ->
                                    val intent =
                                        Intent(
                                            this@DetailHomeActivity,
                                            BookersBiodataActivity::class.java
                                        ).apply {
                                            putExtra("EXTRAS_FLIGHT_DETAIL", item)
                                            putExtra("EXTRA_ADULT_COUNT", adultCount)
                                            putExtra("EXTRA_CHILD_COUNT", childCount)
                                            putExtra("EXTRA_BABY_COUNT", babyCount)
                                        }
//                                    startActivity(intent)
                                }
                            }
                        addAll(data)
                    }
                }

            adapter.clear()
            // Ensure sections is not null and of the correct type
            adapter.update(sections.filterNotNull() as Collection<Group>)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupBinding(ticket: FlightDetailTicket) {
        binding.headerDetailTicketHome.tvTitleHeader.text = getString(R.string.text_flight_detail)
        binding.rvDetailCard.apply {
            layoutManager = LinearLayoutManager(this@DetailHomeActivity)
            adapter = this@DetailHomeActivity.adapter
        }
    }

    private fun setOnClickListener() {
        binding.headerDetailTicketHome.ivBack.setOnClickListener {
            finish()
        }
        binding.btnSelectTicket.setOnClickListener {
            detailViewModel.getDetailTicketById(extraId!!, extraSeatClass!!)
                .observe(this@DetailHomeActivity) { result ->
                    result.proceedWhen(
                        doOnSuccess = {
                            it.payload?.let { flightDetail ->
                                val intent =
                                    Intent(this, BookersBiodataActivity::class.java).apply {
                                        putExtra("EXTRAS_FLIGHT_DETAIL", flightDetail)
                                        putExtra("EXTRA_ADULT_COUNT", adultCount)
                                        putExtra("EXTRA_CHILD_COUNT", childCount)
                                        putExtra("EXTRA_BABY_COUNT", babyCount)
                                    }
                                startActivity(intent)
                            }
                        },
                        doOnError = {
                            Log.d("Error from Detail", "${it.message}")
                        },
                    )
                }
        }
    }
    companion object{
        const val EXTRA_TICKET = "EXTRA_TICKET"
        fun startActivity(
            context: Context,
            ticket: List<FlightTicket>
        ){
            val intent = Intent(context, DetailHomeActivity::class.java)

        }
    }
}
