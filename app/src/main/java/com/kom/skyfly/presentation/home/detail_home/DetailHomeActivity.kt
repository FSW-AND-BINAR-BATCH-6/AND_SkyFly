package com.kom.skyfly.presentation.home.detail_home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kom.skyfly.data.model.home.flight_detail.FlightDetailTicket
import com.kom.skyfly.databinding.ActivityDetailHomeBinding
import com.kom.skyfly.presentation.checkout.bookersbiodata.BookersBiodataActivity
import com.kom.skyfly.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailHomeActivity : AppCompatActivity() {
    private val binding: ActivityDetailHomeBinding by lazy {
        ActivityDetailHomeBinding.inflate(layoutInflater)
    }

    private var extraId: String? = null
    private var extraSeatClass: String? = null

    private val detailViewModel: DetailHomeViewModel by viewModel()
    private var adultCount: Int? = 0
    private var childCount: Int? = 0
    private var babyCount: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        extraId = intent.getStringExtra("EXTRA_FLIGHT_ID")
        extraSeatClass = intent.getStringExtra("EXTRA_FLIGHT_SEATCLASS")
        adultCount = intent.getIntExtra("EXTRA_ADULT_COUNT", 0)
        childCount = intent.getIntExtra("EXTRA_CHILD_COUNT", 0)
        babyCount = intent.getIntExtra("EXTRA_BABY_COUNT", 0)
        setOnClickListener()
        observeData()
    }

    private fun observeData() {
        detailViewModel.getDetailTicketById(extraId!!, extraSeatClass!!)
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

    @SuppressLint("SetTextI18n")
    private fun setupBinding(ticket: FlightDetailTicket) {
        ticket.let {
            binding.layoutDetailCard.tvFlightDestinationDetailTicket.text =
                "${it.departureCity} - > ${it.arrivalCity} (${it.duration})"
            binding.layoutDetailCard.tvDepartureTimeDetailTicket.text = it.departureTime
            binding.layoutDetailCard.tvDepartureDateDetailTicket.text = it.departureDate
            binding.layoutDetailCard.tvDepartureAirportDetailTicket.text = it.departureAirport
            binding.layoutDetailCard.tvTerminalDetailTicket.text = it.departureTerminal
            binding.layoutDetailCard.tvAirplaneNameDetailTicket.text = it.airplaneName
            binding.layoutDetailCard.tvSeatClassDetailTicket.text = it.seatClass
            binding.layoutDetailCard.tvAirplaneCodeDetailTicket.text = it.code
            binding.layoutDetailCard.tvFacilitiesDetailTicket.text = it.facilities
            binding.layoutDetailCard.tvArrivalTimeDetailTicket.text = it.arrivalTime
            binding.layoutDetailCard.tvArrivalDateDetailTicket.text = it.arrivalDate
            binding.layoutDetailCard.tvDestinationAirportDetailTicket.text = it.arrivalAirport
        }
    }

    private fun setOnClickListener() {
        binding.headerDetailTicketHome.ivBackBtnDetailTicketHome.setOnClickListener {
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
}
