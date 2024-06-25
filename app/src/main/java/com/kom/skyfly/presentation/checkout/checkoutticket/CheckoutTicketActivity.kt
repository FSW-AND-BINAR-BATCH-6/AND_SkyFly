package com.kom.skyfly.presentation.checkout.checkoutticket

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.kom.skyfly.R
import com.kom.skyfly.core.BaseActivity
import com.kom.skyfly.data.model.transaction.detail.TransactionDetailResponses
import com.kom.skyfly.databinding.ActivityCheckoutTicketBinding
import com.kom.skyfly.presentation.checkout.payment.PaymentActivity
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.utils.NoInternetException
import com.kom.skyfly.utils.ServerErrorException
import com.kom.skyfly.utils.UnAuthorizeException
import com.kom.skyfly.utils.formatToRupiah
import com.kom.skyfly.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutTicketActivity : BaseActivity() {
    private val binding: ActivityCheckoutTicketBinding by lazy {
        ActivityCheckoutTicketBinding.inflate(layoutInflater)
    }

    private val checkoutTicketViewModel: CheckoutTicketViewModel by viewModel()

    private var paymentUrlTicket: String? = null
    private var transactionId: String? = null
    private var adult: Int = 0
    private var children: Int = 0
    private var baby: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setTitleHeader()
        paymentUrlTicket = intent.getStringExtra("EXTRAS_PAYMENT_URL")
        transactionId = intent.getStringExtra("EXTRAS_TRANSACTION_ID")
        adult = intent.getIntExtra("EXTRAS_ADULT", 0)
        children = intent.getIntExtra("EXTRAS_CHILD", 0)
        baby = intent.getIntExtra("EXTRAS_BABY", 0)
        setClickListeners()
        getTransactionDataById(transactionId)
    }

    private fun getTransactionDataById(id: String?) {
        if (id != null) {
            checkoutTicketViewModel.getTransactionById(id).observe(this) { result ->
                result.proceedWhen(
                    doOnSuccess = {
                        binding.btnProceedToPayment.isEnabled = true
                        binding.main.isRefreshing = false
                        binding.shmProgressFlightTicket.isVisible = false
                        binding.layoutFlightDetails.cvFlightDetails.isVisible = true
                        binding.layoutFlightDetails.cvPriceDetails.isVisible = true
                        binding.layoutFlightDetails.tvTrip.isVisible = true
                        val response = result.payload
                        setTransactionDetailData(response)
                    },
                    doOnError = {
                        binding.btnProceedToPayment.isEnabled = false
                        binding.shmProgressFlightTicket.isVisible = false
                        binding.main.isRefreshing = false
                        if (it.exception is NoInternetException) {
                            binding.csvCheckoutTicket.setState(
                                ContentState.ERROR_NETWORK_GENERAL,
                                "No internet connection!!",
                            )
                        } else if (it.exception is UnAuthorizeException) {
                            errorHandler(it.exception)
                            binding.csvCheckoutTicket.setState(
                                ContentState.ERROR_NETWORK_GENERAL,
                                getString(R.string.text_session_expired_please_login_again),
                            )
                        } else if (it.exception is ServerErrorException) {
                            errorHandler(it.exception)
                            binding.csvCheckoutTicket.setState(
                                ContentState.ERROR_NETWORK,
                                getString(R.string.text_something_went_wrong),
                                R.drawable.img_empty_data,
                            )
                        }
                        Log.d(
                            "GetTransactionByIdError",
                            "getTransactionDataById: ${it.exception?.message}",
                        )
                    },
                    doOnLoading = {
                        binding.layoutFlightDetails.cvFlightDetails.isVisible = false
                        binding.layoutFlightDetails.cvPriceDetails.isVisible = false
                        binding.layoutFlightDetails.tvTrip.isVisible = false
                        binding.main.isRefreshing = true
                        binding.btnProceedToPayment.isEnabled = false
                        binding.shmProgressFlightTicket.isVisible = true
                    },
                )
            }
        }
    }

    @SuppressLint("SetTextI18n")
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
        var passengerCategory: String? = null

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
            tvTrip.text =
                "$departureCity -> $destinationCity ($flightDuration)"
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

    private fun setClickListeners() {
        binding.btnProceedToPayment.setOnClickListener {
            val paymentUrl = paymentUrlTicket
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("payment_url", paymentUrl)
            intent.putExtra("EXTRAS_TRANSACTION_ID", transactionId)
            intent.putExtra("EXTRAS_ADULT", adult)
            intent.putExtra("EXTRAS_CHILD", children)
            intent.putExtra("EXTRAS_BABY", baby)
            startActivity(intent)
        }
        binding.layoutHeader.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.main.setOnRefreshListener {
            getTransactionDataById(transactionId)
        }
    }

    private fun setTitleHeader() {
        binding.layoutHeader.tvTitleHeader.text = getString(R.string.text_header_detail_history)
    }
}
