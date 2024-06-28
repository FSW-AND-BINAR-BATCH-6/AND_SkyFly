package com.kom.skyfly.presentation.home.detail_home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.kom.skyfly.R
import com.kom.skyfly.core.BaseActivity
import com.kom.skyfly.data.model.home.flight_detail.FlightDetailTicket
import com.kom.skyfly.databinding.ActivityDetailHomeBinding
import com.kom.skyfly.presentation.checkout.bookersbiodata.BookersBiodataActivity
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.utils.NoInternetException
import com.kom.skyfly.utils.ServerErrorException
import com.kom.skyfly.utils.UnAuthorizeException
import com.kom.skyfly.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailHomeActivity : BaseActivity() {
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
                    doOnLoading = {
                        binding.shimmerDetailTicket.isVisible = true
                        binding.svDetailTicket.isVisible = false
                        binding.btnSelectTicket.isEnabled = false
                        binding.headerDetailTicketHome.tvTitleHeader.isVisible = true
                    },
                    doOnSuccess = {
                        Handler(Looper.getMainLooper()).postDelayed({
                            binding.shimmerDetailTicket.isVisible = false
                            binding.svDetailTicket.isVisible = true
                            binding.btnSelectTicket.isEnabled = true
                            it.payload?.let { flightDetail ->
                                Log.d("detailTicket", "$flightDetail")
                                setupBinding(flightDetail)
                            }
                        }, 1000)
                    },
                    doOnError = { error ->
                        binding.btnSelectTicket.isEnabled = false
                        binding.shimmerDetailTicket.isVisible = false
                        if (error.exception is NoInternetException) {
                            binding.csvDetailTicket.setState(
                                ContentState.ERROR_NETWORK,
                                getString(R.string.no_internet_connection),
                            )
                        } else if (error.exception is UnAuthorizeException) {
                            errorHandler(error.exception)
                            binding.csvDetailTicket.setState(
                                ContentState.ERROR_NETWORK_GENERAL,
                                getString(R.string.text_session_expired_please_login_again),
                            )
                        } else if (error.exception is ServerErrorException) {
                            errorHandler(error.exception)
                            binding.csvDetailTicket.setState(
                                ContentState.ERROR_NETWORK,
                                getString(R.string.text_server_error_please_try_again_later),
                                R.drawable.img_empty_data,
                            )
                        } else {
                            binding.csvDetailTicket.setState(ContentState.ERROR_GENERAL)
                        }
                        Log.e("ChooseSeatActivity", "Error: ${error.exception?.message}")
                    },
                    doOnEmpty = {
                        binding.shimmerDetailTicket.isVisible = false
                        binding.svDetailTicket.isVisible = false
                        binding.btnSelectTicket.isVisible = false
                    },
                )
            }
    }

    @SuppressLint("SetTextI18n")
    private fun setupBinding(ticket: FlightDetailTicket) {
        binding.headerDetailTicketHome.tvTitleHeader.text = getString(R.string.text_flight_detail)
        ticket.let {
            binding.layoutDetailCard.tvFlightDestinationDetailTicket.text =
                "${it.departureCity} - > ${it.arrivalCity} (${it.duration})"
            binding.layoutDetailCard.tvDepartureTimeDetailTicket.text = it.departureTime
            binding.layoutDetailCard.tvDepartureDateDetailTicket.text = it.departureDate
            binding.layoutDetailCard.tvDepartureAirportDetailTicket.text =
                "${it.departureAirport} - ${it.departureTerminal}"
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
                        doOnError = { error ->
                            Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT)
                                .show()
                        },
                    )
                }
        }
    }
}
