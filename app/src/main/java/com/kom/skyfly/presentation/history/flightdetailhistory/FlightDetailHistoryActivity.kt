package com.kom.skyfly.presentation.history.flightdetailhistory

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.kom.skyfly.R
import com.kom.skyfly.core.BaseActivity
import com.kom.skyfly.data.model.transaction.detail.TransactionDetailResponses
import com.kom.skyfly.databinding.ActivityFlightDetailHistoryBinding
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.utils.NoInternetException
import com.kom.skyfly.utils.ServerErrorException
import com.kom.skyfly.utils.UnAuthorizeException
import com.kom.skyfly.utils.formatToRupiah
import com.kom.skyfly.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class FlightDetailHistoryActivity : BaseActivity() {
    private val binding: ActivityFlightDetailHistoryBinding by lazy {
        ActivityFlightDetailHistoryBinding.inflate(layoutInflater)
    }

    private val detailHistoryViewModel: FlightDetailHistoryViewModel by viewModel()
    private var transactionIdFromExtras: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        transactionIdFromExtras = intent.getStringExtra("EXTRAS_TRANSACTION_ID")
        setOnClickListeners()
        observeData(transactionIdFromExtras)
    }

    private fun setOnClickListeners() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun observeData(id: String?) {
        if (id != null) {
            detailHistoryViewModel.getHistoryById(id).observe(this) { result ->
                result.proceedWhen(
                    doOnSuccess = {
                        Handler(Looper.getMainLooper()).postDelayed({
                            binding.shimmerDetailHistory.isVisible = false
                            binding.svFlightDetails.isVisible = true
                            binding.btnProceedToPayment.isEnabled = true
                            setupBind(it.payload)
                        }, 1000)
                    },
                    doOnLoading = {
                        binding.shimmerDetailHistory.isVisible = true
                        binding.svFlightDetails.isVisible = false
                        binding.btnProceedToPayment.isEnabled = false
                    },
                    doOnError = { error ->
                        binding.shimmerDetailHistory.isVisible = false

                        if (error.exception is NoInternetException) {
                            binding.csvDetailHistory.setState(
                                ContentState.ERROR_NETWORK,
                                getString(R.string.no_internet_connection),
                            )
                        } else if (error.exception is UnAuthorizeException) {
                            errorHandler(error.exception)
                            binding.csvDetailHistory.setState(
                                ContentState.ERROR_NETWORK_GENERAL,
                                getString(R.string.text_session_expired_please_login_again),
                            )
                        } else if (error.exception is ServerErrorException) {
                            errorHandler(error.exception)
                            binding.csvDetailHistory.setState(
                                ContentState.ERROR_NETWORK,
                                getString(R.string.text_server_error_please_try_again_later),
                                R.drawable.img_empty_data,
                            )
                        } else {
                            binding.csvDetailHistory.setState(ContentState.ERROR_GENERAL)
                        }
                        Log.e("DetailTicketActivity", "Error: ${error.exception?.message}")
                    },
                    doOnEmpty = {
                        binding.shimmerDetailHistory.isVisible = false
                        getString(R.string.text_havent_made_a_booking)
                        binding.svFlightDetails.isVisible = false
                        binding.btnProceedToPayment.isVisible = false
                    },
                )
            }
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun setupBind(historyDetail: TransactionDetailResponses?) {
        val transactionDetails = historyDetail?.data?.transactionDetails
        val paymentStatus = historyDetail?.data?.status

        var adultCount = 0
        var childCount = 0
        var babyCount = 0
        var totalPricePassengerAdult = 0
        var totalPricePassengerChild = 0
        var totalPricePassengerBaby = 0

        transactionDetails?.forEach { detail ->
            when (detail?.passengerCategory) {
                "ADULT" -> {
                    adultCount++
                    totalPricePassengerAdult += detail.totalPrice
                }

                "CHILD" -> {
                    childCount++
                    totalPricePassengerChild += detail.totalPrice
                }

                "INFRANT" -> {
                    babyCount++
                    totalPricePassengerBaby += detail.totalPrice
                }
            }
        }

        with(binding.layoutFlightDetails) {
            tvPaymentStatus.text = paymentStatus
            tvDetailBookingCode.text = historyDetail?.data?.booking?.code
            tvDetailDepartureDate.text =
                historyDetail?.data?.transactionDetails?.first()?.flight?.departure?.date
            tvDetailDepartureTime.text =
                historyDetail?.data?.transactionDetails?.first()?.flight?.departure?.time
            tvDetailDepartureAirport.text =
                getString(
                    R.string.text_detail_departure_airport_and_terminal,
                    historyDetail?.data?.transactionDetails?.first()?.flight?.departureAirport?.name,
                    historyDetail?.data?.transactionDetails?.first()?.flight?.airline?.terminal,
                )
            tvDetailAirline.text =
                getString(
                    R.string.tv_strip,
                    historyDetail?.data?.transactionDetails?.first()!!.flight.airline.name,
                )
            tvDetailClass.text = historyDetail.data.transactionDetails.first()?.seat?.type
            tvDetailFlightNumber.text = historyDetail.data.transactionDetails.first()!!.flight.code
            tvDetailArrivalDate.text =
                historyDetail.data.transactionDetails.first()?.flight?.arrival?.date
            tvDetailArrivalTime.text =
                historyDetail.data.transactionDetails.first()?.flight?.arrival?.time
            tvDetailDestinationAirport.text =
                historyDetail.data.transactionDetails.first()!!.flight.destinationAirport.name
            tvTotalPrice.text = historyDetail.data.totalPrice.formatToRupiah().toString()
            tvTax.text = historyDetail.data.tax.formatToRupiah().toString()

            if (adultCount > 0) {
                tvTotalByAgeGroupAdult.text = getString(R.string.text_adult, adultCount)
                tvTotalPriceByAgeGroupAdult.text =
                    totalPricePassengerAdult.formatToRupiah().toString()
                tvTotalByAgeGroupAdult.isVisible = true
                tvTotalPriceByAgeGroupAdult.isVisible = true
            } else {
                tvTotalByAgeGroupAdult.isVisible = false
                tvTotalPriceByAgeGroupAdult.isVisible = false
            }

            if (childCount > 0) {
                tvTotalByAgeGroupChild.text = getString(R.string.text_child, childCount)
                tvTotalPriceByAgeGroupChild.text =
                    totalPricePassengerChild.formatToRupiah().toString()
                tvTotalByAgeGroupChild.isVisible = true
                tvTotalPriceByAgeGroupChild.isVisible = true
            } else {
                tvTotalByAgeGroupChild.isVisible = false
                tvTotalPriceByAgeGroupChild.isVisible = false
            }

            if (babyCount > 0) {
                tvTotalByAgeGroupBaby.text = getString(R.string.text_baby, babyCount)
                tvTotalPriceByAgeGroupBaby.text =
                    totalPricePassengerBaby.formatToRupiah().toString()
                tvTotalByAgeGroupBaby.isVisible = true
                tvTotalPriceByAgeGroupBaby.isVisible = true
            } else {
                tvTotalByAgeGroupBaby.isVisible = false
                tvTotalPriceByAgeGroupBaby.isVisible = false
            }

            transactionDetails?.forEachIndexed { index, detail ->
                val passengerName = detail?.name ?: "Unknown"
                val citizenship = detail?.citizenship ?: "Unknown"

                tvTitlePassenger.append(
                    "Passenger ${index + 1} : $passengerName\n" +
                        "Citizenship : $citizenship\n",
                )
            }

            when {
                paymentStatus.equals("pending", ignoreCase = true) -> {
                    tvPaymentStatus.setText(R.string.text_unpaid)
                    tvPaymentStatus.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                tvPaymentStatus.context,
                                R.color.red,
                            ),
                        )
                }

                paymentStatus.equals("settlement", ignoreCase = true) -> {
                    tvPaymentStatus.setText(R.string.text_paid)
                    tvPaymentStatus.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                tvPaymentStatus.context,
                                R.color.green,
                            ),
                        )
                }

                else -> {
                    tvPaymentStatus.setText(R.string.text_cancelled)
                    tvPaymentStatus.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                tvPaymentStatus.context,
                                R.color.darkGrey,
                            ),
                        )
                }
            }

            when {
                paymentStatus.equals("pending", ignoreCase = true) -> {
                    binding.btnProceedToPayment.text = getString(R.string.text_proceed_to_payment)
                    binding.btnProceedToPayment.isEnabled = true
                }

                paymentStatus.equals("settlement", ignoreCase = true) -> {
                    binding.btnProceedToPayment.text = getString(R.string.text_payment_complete)
                    binding.btnProceedToPayment.isEnabled = false
                }

                paymentStatus.equals("cancelled", ignoreCase = true) -> {
                    binding.btnProceedToPayment.text = getString(R.string.text_payment_cancelled)
                    binding.btnProceedToPayment.isEnabled = false
                }
            }
        }
    }
}
