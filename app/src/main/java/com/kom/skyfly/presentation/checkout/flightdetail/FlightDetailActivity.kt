package com.kom.skyfly.presentation.checkout.flightdetail

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.kom.skyfly.R
import com.kom.skyfly.core.BaseActivity
import com.kom.skyfly.data.model.transaction.detail.TransactionDetailResponses
import com.kom.skyfly.databinding.ActivityFlightDetailBinding
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.presentation.main.MainActivity
import com.kom.skyfly.utils.NoInternetException
import com.kom.skyfly.utils.ServerErrorException
import com.kom.skyfly.utils.UnAuthorizeException
import com.kom.skyfly.utils.formatToRupiah
import com.kom.skyfly.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class FlightDetailActivity : BaseActivity() {
    private val binding: ActivityFlightDetailBinding by lazy {
        ActivityFlightDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: FlightDetailViewModel by viewModel()
    private var transactionId: String? = null
    private var adult: Int = 0
    private var children: Int = 0
    private var baby: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        transactionId = intent.getStringExtra("EXTRAS_TRANSACTION_ID")
        adult = intent.getIntExtra("EXTRAS_ADULT", 0)
        children = intent.getIntExtra("EXTRAS_CHILD", 0)
        baby = intent.getIntExtra("EXTRAS_BABY", 0)
        setTitleHeader()
        setSwipeRefresh()
        setOnClickListeners()
        getTicketDetail(transactionId)
    }

    private fun setOnClickListeners() {
        binding.layoutHeader.ivBack.setOnClickListener {
            navigateToHome()
        }
        binding.btnToHome.setOnClickListener {
            navigateToHome()
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun setTitleHeader() {
        binding.layoutHeader.tvTitleHeader.text = getString(R.string.text_ticket_detail)
    }

    private fun setSwipeRefresh() {
        binding.main.setOnRefreshListener {
            getTicketDetail(transactionId)
        }
    }

    private fun getTicketDetail(id: String?) {
        if (id != null) {
            viewModel.getTransactionById(id).observe(this) { result ->
                result.proceedWhen(
                    doOnSuccess = {
                        binding.btnToHome.isEnabled = true
                        binding.layoutFlightDetails.cvFlightDetails.isVisible = true
                        binding.layoutFlightDetails.cvPriceDetails.isVisible = true
                        binding.layoutFlightDetails.tvPaymentStatus.isVisible = true
                        binding.main.isRefreshing = false
                        val response = result.payload
                        binding.shmProgressFlightTicket.isVisible = false
                        setTransactionDetailData(response)
                        Log.d("GetTransactionById", "getTransactionDataById: $response")
                    },
                    doOnError = {
                        binding.btnToHome.isEnabled = true
                        binding.main.isRefreshing = false
                        binding.shmProgressFlightTicket.isVisible = false
                        when (it.exception) {
                            is NoInternetException -> {
                                binding.csvFlightDetail.setState(
                                    ContentState.ERROR_NETWORK_GENERAL,
                                    getString(R.string.no_internet_connection),
                                )
                            }

                            is UnAuthorizeException -> {
                                errorHandler(it.exception)
                                binding.csvFlightDetail.setState(
                                    ContentState.ERROR_NETWORK_GENERAL,
                                    getString(R.string.text_session_expired_please_login_again),
                                )
                            }

                            is ServerErrorException -> {
                                errorHandler(it.exception)
                                binding.csvFlightDetail.setState(
                                    ContentState.ERROR_NETWORK,
                                    getString(R.string.text_something_went_wrong),
                                    R.drawable.img_empty_data,
                                )
                            }
                        }
                        Log.d(
                            "GetTransactionByIdError",
                            "getTransactionDataById: ${it.exception?.message}",
                        )
                    },
                    doOnLoading = {
                        binding.csvFlightDetail.isVisible = false
                        binding.btnToHome.isEnabled = false
                        binding.layoutFlightDetails.cvFlightDetails.isVisible = false
                        binding.layoutFlightDetails.cvPriceDetails.isVisible = false
                        binding.layoutFlightDetails.tvPaymentStatus.isVisible = false
                        binding.main.isRefreshing = true
                        binding.shmProgressFlightTicket.isVisible = true
                    },
                )
            }
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun setTransactionDetailData(response: TransactionDetailResponses?) {
        var departureDate: String? = null
        var departureTime: String? = null
        var departureAirport: String? = null
        var departureTerminal: String? = null
        var airlines: String? = null
        var seatClass: String? = null
        var flightNumber: String? = null
        var arrivalDate: String? = null
        var arrivalTime: String? = null
        var destinationAirport: String? = null
        val tax: Int? = response?.data?.tax
        val totalPrice: Int? = response?.data?.totalPrice
        var totalPricePassengerAdult: Int? = 0
        var totalPricePassengerChild: Int? = 0
        var totalPricePassengerBaby: Int? = 0
        var passengerCategory: String?
        val paymentStatus: String? = response?.data?.status
        val bookingCode: String? = response?.data?.booking?.code
        val transactionDetails = response?.data?.transactionDetails

        response?.data?.transactionDetails?.map {
            departureDate = it?.flight?.departure?.date
            departureTime = it?.flight?.departure?.time
            arrivalDate = it?.flight?.arrival?.date
            arrivalTime = it?.flight?.arrival?.time
            departureAirport = it?.flight?.departureAirport?.name
            departureTerminal = it?.flight?.airline?.terminal
            flightNumber = it?.flight?.airline?.code
            destinationAirport = it?.flight?.destinationAirport?.name
            airlines = it?.flight?.airline?.name
            seatClass = it?.seat?.type
            passengerCategory = it?.passengerCategory
            when (passengerCategory) {
                "ADULT" -> {
                    totalPricePassengerAdult = it?.totalPrice?.times(adult)
                }

                "CHILD" -> {
                    totalPricePassengerChild = it?.totalPrice?.times(children)
                }

                "INFRANT" -> {
                    totalPricePassengerBaby = it?.totalPrice?.times(baby)
                }
            }
        }
        binding.layoutFlightDetails.tvTitlePassenger.text = getString(R.string.text_empty)
        binding.layoutFlightDetails.tvCitizenship.text = getString(R.string.text_empty)
        with(binding.layoutFlightDetails) {
            tvDetailDepartureDate.text = departureDate
            tvDetailDepartureTime.text = departureTime
            tvDetailDepartureAirport.text =
                getString(R.string.text_departure_terminal, departureAirport, departureTerminal)
            tvDetailAirline.text = getString(R.string.text_detail_airlines, airlines)
            tvDetailClass.text = seatClass
            tvDetailFlightNumber.text = flightNumber
            tvDetailArrivalDate.text = arrivalDate
            tvDetailArrivalTime.text = arrivalTime
            tvDetailDestinationAirport.text = destinationAirport
            tvTax.text = tax.formatToRupiah().toString()
            tvTotalPrice.text = totalPrice.formatToRupiah().toString()
            tvDetailBookingCode.text = bookingCode

            transactionDetails?.forEachIndexed { index, detail ->
                val passengerName = detail?.name ?: "Unknown"
                val userPassportId = detail?.passport ?: "Unknown"

                tvTitlePassenger.append(
                    "Passenger ${index + 1} : $passengerName\n" +
                        "ID : $userPassportId\n",
                )
            }

            if (paymentStatus.equals("pending", ignoreCase = true)) {
                tvPaymentStatus.text = getString(R.string.text_unpaid)
                tvPaymentStatus.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            tvPaymentStatus.context,
                            R.color.red,
                        ),
                    )
            } else if (paymentStatus.equals("settlement", ignoreCase = true)) {
                tvPaymentStatus.text = getString(R.string.text_paid)
                tvPaymentStatus.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            tvPaymentStatus.context,
                            R.color.green,
                        ),
                    )
            } else {
                tvPaymentStatus.text = getString(R.string.text_cancelled)
                tvPaymentStatus.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            tvPaymentStatus.context,
                            R.color.darkGrey,
                        ),
                    )
            }

            if (adult > 0) {
                tvTotalByAgeGroupAdult.text = getString(R.string.text_adult_total, adult)
                tvTotalPriceByAgeGroupAdult.text =
                    totalPricePassengerAdult.formatToRupiah().toString()
                tvTotalByAgeGroupAdult.isVisible = true
                tvTotalPriceByAgeGroupAdult.isVisible = true
            } else {
                tvTotalByAgeGroupAdult.isVisible = false
                tvTotalPriceByAgeGroupAdult.isVisible = false
            }

            if (children > 0) {
                tvTotalByAgeGroupChild.text = getString(R.string.text_child_total, children)
                tvTotalPriceByAgeGroupChild.text =
                    totalPricePassengerChild.formatToRupiah().toString()
                tvTotalByAgeGroupChild.isVisible = true
                tvTotalPriceByAgeGroupChild.isVisible = true
            } else {
                tvTotalByAgeGroupChild.isVisible = false
                tvTotalPriceByAgeGroupChild.isVisible = false
            }

            if (baby > 0) {
                tvTotalByAgeGroupBaby.text = getString(R.string.text_baby_total, baby)
                tvTotalPriceByAgeGroupBaby.text =
                    totalPricePassengerBaby.formatToRupiah().toString()
                tvTotalByAgeGroupBaby.isVisible = true
                tvTotalPriceByAgeGroupBaby.isVisible = true
            } else {
                tvTotalByAgeGroupBaby.isVisible = false
                tvTotalPriceByAgeGroupBaby.isVisible = false
            }
        }
    }
}
