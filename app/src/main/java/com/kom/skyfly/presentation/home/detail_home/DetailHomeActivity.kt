package com.kom.skyfly.presentation.home.detail_home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ScrollView
import androidx.core.view.isVisible
import coil.load
import com.kom.skyfly.R
import com.kom.skyfly.core.BaseActivity
import com.kom.skyfly.data.model.home.flight_detail.FlightDetailTicket
import com.kom.skyfly.data.model.home.intent.SearchResultIntent
import com.kom.skyfly.databinding.ActivityDetailHomeBinding
import com.kom.skyfly.presentation.checkout.bookersbiodata.BookersBiodataActivity
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.utils.NoInternetException
import com.kom.skyfly.utils.ServerErrorException
import com.kom.skyfly.utils.UnAuthorizeException
import com.kom.skyfly.utils.proceedWhen
import com.xwray.groupie.GroupieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailHomeActivity : BaseActivity() {
    private val binding: ActivityDetailHomeBinding by lazy {
        ActivityDetailHomeBinding.inflate(layoutInflater)
    }
    private val adapter: GroupieAdapter by lazy {
        GroupieAdapter()
    }
    private lateinit var searchResultIntent: SearchResultIntent

    // Define global variables
    private var returnId: String? = null
    private var departureId: String? = null
    private var seatClass: String? = null
    private var adultCount: Int? = null
    private var childCount: Int? = null
    private var babyCount: Int? = null
    private var roundTrip: Boolean? = null
    private val detailViewModel: DetailHomeViewModel by viewModel()
//    private var extraRoundTrip: Boolean? = null
//    private var extraId: String? = null
//    private var extraSeatClass: String? = null
//    private var adultCount: Int? = 0
//    private var childCount: Int? = 0
//    private var babyCount: Int? = 0
//    private var returnFlightId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        searchResultIntent = intent.getParcelableExtra("EXTRA_SEARCH_RESULT") ?: return
        returnId = searchResultIntent.returnId
        departureId = searchResultIntent.departureId
        seatClass = searchResultIntent.seatClass
        adultCount = searchResultIntent.adultCount
        childCount = searchResultIntent.childCount
        babyCount = searchResultIntent.babyCount
        roundTrip = searchResultIntent.roundTrip
        setOnClickListener()
        observeData()
    }

    private fun observeData() {
        if (roundTrip == true) {
            getDepartureDetail()
            getReturnDetail()
        } else {
            getDepartureDetail()
        }
    }

    private fun getDepartureDetail() {
        detailViewModel.getDetailTicketById(departureId!!, seatClass!!)
            .observe(this@DetailHomeActivity) { result ->
                result.proceedWhen(
                    doOnSuccess = {
                        Handler(Looper.getMainLooper()).postDelayed({
                            val scrollView = findViewById<ScrollView>(R.id.sv_detail_ticket)
                            val layoutDetailCard = scrollView.findViewById<View>(R.id.layout_detail_card)
                            binding.shimmerDetailTicket.isVisible = false
                            binding.svDetailTicket.isVisible = true
                            binding.btnSelectTicket.isEnabled = true
                            layoutDetailCard.isVisible = true
                            it.payload?.let { flightDetail ->
                                setupBinding(flightDetail)
                                binding.btnSelectTicket.setOnClickListener {
                                    val intent =
                                        Intent(this, BookersBiodataActivity::class.java).apply {
                                            putExtra("EXTRAS_FLIGHT_DETAIL", flightDetail)
                                            putExtra("EXTRA_ADULT_COUNT", adultCount)
                                            putExtra("EXTRA_CHILD_COUNT", childCount)
                                            putExtra("EXTRA_BABY_COUNT", babyCount)
                                        }
                                    startActivity(intent)
                                }
                            }
                        }, 1000)
                        Log.d("Succes from Detail", "halo")
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

    private fun getReturnDetail() {
        detailViewModel.getDetailTicketById(returnId!!, seatClass!!)
            .observe(this@DetailHomeActivity) { result ->
                result.proceedWhen(
                    doOnSuccess = {
                        Handler(Looper.getMainLooper()).postDelayed({
                            val scrollView = findViewById<ScrollView>(R.id.sv_detail_ticket)
                            val layoutDetailCard = scrollView.findViewById<View>(R.id.layout_detail_return_card)
                            binding.shimmerDetailTicket.isVisible = false
                            binding.svDetailTicket.isVisible = true
                            binding.btnSelectTicket.isEnabled = true
                            layoutDetailCard.isVisible = true

                            it.payload?.let { flightDetail ->
                                setupBindingReturnTicket(flightDetail)
                                binding.btnSelectTicket.setOnClickListener {
                                    val intent =
                                        Intent(this, BookersBiodataActivity::class.java).apply {
                                            putExtra("EXTRAS_FLIGHT_DETAIL", flightDetail)
                                            putExtra("EXTRA_ADULT_COUNT", adultCount)
                                            putExtra("EXTRA_CHILD_COUNT", childCount)
                                            putExtra("EXTRA_BABY_COUNT", babyCount)
                                        }
                                    startActivity(intent)
                                }
                            }
                        }, 1000)
                        Log.d("Succes from Detail", "halo")
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
        ticket.let {
            binding.layoutDetailCard.ivAirlineDetailTicket.load(it.airplaneImg) {
                crossfade(true)
                error(R.mipmap.ic_launcher)
            }
            binding.layoutDetailCard.tvFlightDestinationDetailTicket.text =
                "${it.departureCity} - > ${it.arrivalCity} (${it.duration})"
            binding.layoutDetailCard.tvDepartureTimeDetailTicket.text = it.departureTime
            binding.layoutDetailCard.tvDepartureDateDetailTicket.text = it.departureDate
            binding.layoutDetailCard.tvDepartureAirportDetailTicket.text = "${it.departureAirport} - ${it.departureTerminal}"
            binding.layoutDetailCard.tvAirplaneNameDetailTicket.text = it.airplaneName
            binding.layoutDetailCard.tvSeatClassDetailTicket.text = it.seatClass
            binding.layoutDetailCard.tvAirplaneCodeDetailTicket.text = it.code
            binding.layoutDetailCard.tvFacilitiesDetailTicket.text = it.facilities
            binding.layoutDetailCard.tvArrivalTimeDetailTicket.text = it.arrivalTime
            binding.layoutDetailCard.tvArrivalDateDetailTicket.text = it.arrivalDate
            binding.layoutDetailCard.tvDestinationAirportDetailTicket.text = it.arrivalAirport
        }
    }

    private fun setupBindingReturnTicket(ticket: FlightDetailTicket) {
        ticket.let {
            binding.layoutDetailReturnCard.ivAirlineDetailTicket.load(it.airplaneImg) {
                crossfade(true)
                error(R.mipmap.ic_launcher)
            }
            binding.layoutDetailReturnCard.tvFlightDestinationDetailTicket.text =
                getString(
                    R.string.text_detail_return_flight_title,
                    it.departureCity,
                    it.arrivalCity,
                    it.duration,
                )
            binding.layoutDetailReturnCard.tvDepartureTimeDetailTicket.text = it.departureTime
            binding.layoutDetailReturnCard.tvDepartureDateDetailTicket.text = it.departureDate
            binding.layoutDetailReturnCard.tvDepartureAirportDetailTicket.text = "${it.departureAirport} - ${it.departureTerminal}"
            binding.layoutDetailReturnCard.tvAirplaneNameDetailTicket.text = it.airplaneName
            binding.layoutDetailReturnCard.tvSeatClassDetailTicket.text = it.seatClass
            binding.layoutDetailReturnCard.tvAirplaneCodeDetailTicket.text = it.code
            binding.layoutDetailReturnCard.tvFacilitiesDetailTicket.text = it.facilities
            binding.layoutDetailReturnCard.tvArrivalTimeDetailTicket.text = it.arrivalTime
            binding.layoutDetailReturnCard.tvArrivalDateDetailTicket.text = it.arrivalDate
            binding.layoutDetailReturnCard.tvDestinationAirportDetailTicket.text = it.arrivalAirport
        }
    }

    private fun setOnClickListener() {
        binding.headerDetailTicketHome.ivBack.setOnClickListener {
            finish()
        }
        binding.btnSelectTicket.setOnClickListener {
            detailViewModel.getDetailTicketById(returnId!!, seatClass!!)
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
