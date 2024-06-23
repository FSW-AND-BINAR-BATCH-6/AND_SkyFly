package com.kom.skyfly.presentation.checkout.flightdetail

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.kom.skyfly.R
import com.kom.skyfly.data.model.transaction.detail.TransactionDetailResponses
import com.kom.skyfly.databinding.ActivityFlightDetailBinding
import com.kom.skyfly.utils.formatToRupiah
import com.kom.skyfly.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class FlightDetailActivity : AppCompatActivity() {
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
        setSwipeRefresh()
        transactionId = intent.getStringExtra("EXTRAS_TRANSACTION_ID")
        adult = intent.getIntExtra("EXTRAS_ADULT", 0)
        children = intent.getIntExtra("EXTRAS_CHILD", 0)
        baby = intent.getIntExtra("EXTRAS_BABY", 0)
        getTicketDetail(transactionId)
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
                        binding.main.isRefreshing = false
                        val response = result.payload
                        setTransactionDetailData(response)
                        Log.d("GetTransactionById", "getTransactionDataById: $response")
                    },
                    doOnError = {
                        binding.main.isRefreshing = false
                        Log.d(
                            "GetTransactionByIdError",
                            "getTransactionDataById: ${it.exception?.message}",
                        )
                    },
                    doOnLoading = {
                        binding.main.isRefreshing = true
                    },
                )
            }
        }
    }

    private fun setTransactionDetailData(response: TransactionDetailResponses?) {
        var departureDate: String? = null
        var departureTime: String? = null
        var departureAirport: String? = null
        var departureTerminal: String? = null
        var departureCity: String? = null
        var airlines: String? = null
        var seatClass: String? = null
        var flightNumber: String? = null
        var arrivalDate: String? = null
        var arrivalTime: String? = null
        var destinationAirport: String? = null
        var destinationCity: String? = null
        var flightDuration: String? = null
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
            departureCity = it?.flight?.departureAirport?.city
            arrivalDate = it?.flight?.arrival?.date
            arrivalTime = it?.flight?.arrival?.time
            departureAirport = it?.flight?.departureAirport?.name
            departureTerminal = it?.flight?.airline?.terminal
            destinationCity = it?.flight?.destinationAirport?.city
            flightNumber = it?.flight?.airline?.code
            destinationAirport = it?.flight?.destinationAirport?.name
            flightDuration = it?.flight?.flightDuration
            airlines = it?.flight?.airline?.name
            seatClass = it?.seat?.type
            passengerCategory = it?.passengerCategory
            if (passengerCategory == "ADULT") {
                totalPricePassengerAdult = it?.totalPrice?.times(adult)
            } else if (passengerCategory == "CHILD") {
                totalPricePassengerChild = it?.totalPrice?.times(children)
            } else if (passengerCategory == "INFRANT") {
                totalPricePassengerBaby = it?.totalPrice?.times(baby)
            }
        }
        with(binding.layoutFlightDetails) {
            tvDetailDepartureDate.text = departureDate
            tvDetailDepartureTime.text = departureTime
            tvDetailDepartureAirport.text = "$departureAirport -"
            tvDetailTerminal.text = departureTerminal
            tvDetailAirline.text = "$airlines - "
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
                val citizenship = detail?.citizenship ?: "Unknown"

                tvTitlePassenger.append(
                    "Passenger ${index + 1} : $passengerName\n" +
                        "Citizenship : $citizenship\n",
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
                tvTotalByAgeGroupAdult.text = "$adult Adult"
                tvTotalPriceByAgeGroupAdult.text =
                    totalPricePassengerAdult.formatToRupiah().toString()
                tvTotalByAgeGroupAdult.isVisible = true
                tvTotalPriceByAgeGroupAdult.isVisible = true
            } else {
                tvTotalByAgeGroupAdult.isVisible = false
                tvTotalPriceByAgeGroupAdult.isVisible = false
            }

            if (children > 0) {
                tvTotalByAgeGroupChild.text = "$children Child"
                tvTotalPriceByAgeGroupChild.text =
                    totalPricePassengerChild.formatToRupiah().toString()
                tvTotalByAgeGroupChild.isVisible = true
                tvTotalPriceByAgeGroupChild.isVisible = true
            } else {
                tvTotalByAgeGroupChild.isVisible = false
                tvTotalPriceByAgeGroupChild.isVisible = false
            }

            if (baby > 0) {
                tvTotalByAgeGroupBaby.text = "$baby Baby"
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
