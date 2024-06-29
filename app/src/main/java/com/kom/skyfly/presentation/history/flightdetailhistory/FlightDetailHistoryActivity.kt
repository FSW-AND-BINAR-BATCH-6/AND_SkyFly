package com.kom.skyfly.presentation.history.flightdetailhistory

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import coil.load
import com.kom.skyfly.R
import com.kom.skyfly.core.BaseActivity
import com.kom.skyfly.data.model.transaction.detail.TransactionDetailResponses
import com.kom.skyfly.data.model.transaction.paymentstatus.ItemsPaymentStatus
import com.kom.skyfly.databinding.ActivityFlightDetailHistoryBinding
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.utils.NoInternetException
import com.kom.skyfly.utils.ServerErrorException
import com.kom.skyfly.utils.UnAuthorizeException
import com.kom.skyfly.utils.formatToRupiah
import com.kom.skyfly.utils.proceedWhen
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.viewModel

class FlightDetailHistoryActivity : BaseActivity() {
    private val binding: ActivityFlightDetailHistoryBinding by lazy {
        ActivityFlightDetailHistoryBinding.inflate(layoutInflater)
    }

    private val detailHistoryViewModel: FlightDetailHistoryViewModel by viewModel()
    private var transactionIdFromExtras: String? = null
    private var historyDetails: TransactionDetailResponses? = null
    private var vaNumbers: ItemsPaymentStatus? = null
    private var orderId: String = ""

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
        binding.main.setOnRefreshListener {
            observeData(transactionIdFromExtras)
        }
        binding.btnCancelTransaction.setOnClickListener {
            confirmCancelTransaction()
        }
    }

    private fun confirmCancelTransaction() {
        val dialog =
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.text_are_you_sure_you_want_to_cancel_transaction))
                .setPositiveButton(
                    getString(R.string.text_yes),
                ) { dialog, id ->
                    doCancelTransaction(orderId)
                }
                .setNegativeButton(
                    getString(R.string.text_no),
                ) { dialog, id ->
                }.create()
        dialog.show()
    }

    private fun doCancelTransaction(orderId: String) {
        detailHistoryViewModel.cancelTransaction(orderId).observe(this) { cancelResult ->
            cancelResult.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    Toasty.success(this, "Transaction Cancelled!", Toast.LENGTH_SHORT).show()
                    observeData(transactionIdFromExtras)
                },
                doOnLoading = {
                    binding.btnCancelTransaction.isEnabled = false
                    binding.pbLoading.isVisible = true
                },
                doOnError = { error ->
                    if (error.exception is NoInternetException) {
                        binding.csvFlightDetailsHistory.setState(
                            ContentState.ERROR_NETWORK,
                            getString(R.string.no_internet_connection),
                        )
                    } else if (error.exception is UnAuthorizeException) {
                        errorHandler(error.exception)
                        binding.csvFlightDetailsHistory.setState(
                            ContentState.ERROR_NETWORK_GENERAL,
                            getString(R.string.text_session_expired_please_login_again),
                        )
                    } else if (error.exception is ServerErrorException) {
                        errorHandler(error.exception)
                        binding.csvFlightDetailsHistory.setState(
                            ContentState.ERROR_NETWORK,
                            getString(R.string.text_server_error_please_try_again_later),
                            R.drawable.img_empty_data,
                        )
                    } else {
                        binding.csvFlightDetailsHistory.setState(
                            ContentState.ERROR_GENERAL,
                            getString(R.string.text_something_went_wrong),
                        )
                    }
                },
            )
        }
    }

    private fun observePaymentStatus(id: String?) {
        if (id != null) {
            detailHistoryViewModel.getPaymentStatus(id)
                .observe(this) { resultStatus ->
                    resultStatus.proceedWhen(
                        doOnSuccess = {
                            vaNumbers = resultStatus.payload
                            setupBind(historyDetails, vaNumbers)
                        },
                        doOnError = { error ->
                            vaNumbers = null
                        },
                        doOnEmpty = {
                            vaNumbers = null
                        },
                    )
                }
        }
    }

    private fun observeData(id: String?) {
        if (id != null) {
            detailHistoryViewModel.getHistoryById(id).observe(this) { result ->
                result.proceedWhen(
                    doOnSuccess = {
                        binding.main.isRefreshing = false
                        binding.shimmerDetailHistory.isVisible = false
                        binding.svFlightDetails.isVisible = true
                        historyDetails = it.payload
                        orderId = it.payload?.data?.orderId.toString()
                        observePaymentStatus(orderId)
                        if (vaNumbers == null) {
                            setupBind(historyDetails, null)
                        }
                    },
                    doOnLoading = {
                        binding.shimmerDetailHistory.isVisible = true
                        binding.svFlightDetails.isVisible = false
                        binding.btnCancelTransaction.isEnabled = false
                        binding.main.isRefreshing = true
                    },
                    doOnError = { error ->
                        binding.main.isRefreshing = false
                        if (error.exception is NoInternetException) {
                            binding.csvFlightDetailsHistory.setState(
                                ContentState.ERROR_NETWORK,
                                getString(R.string.no_internet_connection),
                            )
                        } else if (error.exception is UnAuthorizeException) {
                            errorHandler(error.exception)
                            binding.csvFlightDetailsHistory.setState(
                                ContentState.ERROR_NETWORK_GENERAL,
                                getString(R.string.text_session_expired_please_login_again),
                            )
                        } else if (error.exception is ServerErrorException) {
                            errorHandler(error.exception)
                            binding.csvFlightDetailsHistory.setState(
                                ContentState.ERROR_NETWORK,
                                getString(R.string.text_server_error_please_try_again_later),
                                R.drawable.img_empty_data,
                            )
                        } else {
                            binding.csvFlightDetailsHistory.setState(ContentState.ERROR_GENERAL)
                        }
                    },
                    doOnEmpty = {
                        binding.main.isRefreshing = false
                        binding.shimmerDetailHistory.isVisible = false
                        getString(R.string.text_havent_made_a_booking)
                        binding.svFlightDetails.isVisible = false
                        binding.btnCancelTransaction.isVisible = false
                    },
                )
            }
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun setupBind(
        historyDetail: TransactionDetailResponses?,
        itemPaymentStatus: ItemsPaymentStatus?,
    ) {
        val transactionDetails = historyDetail?.data?.transactionDetails
        val paymentStatus = historyDetail?.data?.status

        var adultCount = 0
        var childCount = 0
        var babyCount = 0
        var totalPricePassengerAdult = 0
        var totalPricePassengerChild = 0
        var totalPricePassengerBaby = 0
        var airlineImage: String? = null
        historyDetail?.data?.transactionDetails?.map {
            airlineImage = it?.flight?.airline?.image
        }

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
        binding.layoutFlightDetails.tvTitlePassenger.text = getString(R.string.text_empty)
        binding.layoutFlightDetails.tvCitizenship.text = getString(R.string.text_empty)

        with(binding.layoutFlightDetails) {
            ivAirline.load(
                airlineImage,
            ) {
                error(R.drawable.img_airline)
            }
            tvPaymentStatus.text = paymentStatus
            tvDetailBookingCode.text = historyDetail?.data?.booking?.code
            tvDetailDepartureDate.text =
                historyDetail?.data?.transactionDetails?.firstOrNull()?.flight?.departure?.date
            tvDetailDepartureTime.text =
                historyDetail?.data?.transactionDetails?.firstOrNull()?.flight?.departure?.time
            tvDetailDepartureAirport.text =
                "${historyDetail?.data?.transactionDetails?.firstOrNull()?.flight?.departureAirport?.name} - ${historyDetail?.data?.transactionDetails?.firstOrNull()?.flight?.airline?.terminal}"
            tvDetailAirline.text =
                getString(
                    R.string.tv_strip,
                    historyDetail?.data?.transactionDetails?.firstOrNull()?.flight?.airline?.name,
                )
            tvDetailClass.text = historyDetail?.data?.transactionDetails?.firstOrNull()?.seat?.type
            tvDetailFlightNumber.text =
                historyDetail?.data?.transactionDetails?.firstOrNull()?.flight?.code
            tvDetailArrivalDate.text =
                historyDetail?.data?.transactionDetails?.firstOrNull()?.flight?.arrival?.date
            tvDetailArrivalTime.text =
                historyDetail?.data?.transactionDetails?.firstOrNull()?.flight?.arrival?.time
            tvDetailDestinationAirport.text =
                historyDetail?.data?.transactionDetails?.firstOrNull()?.flight?.destinationAirport?.name
            tvTotalPrice.text = historyDetail?.data?.totalPrice?.formatToRupiah().toString()
            tvTax.text = historyDetail?.data?.tax?.formatToRupiah().toString()

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
                    tvVaNumber.text =
                        itemPaymentStatus?.vaNumbers?.joinToString { it.vaNumber.toString() }
                            ?: "N/A"

                    tvPaymentCodeTitle.isVisible = true
                    tvVaNumber.isVisible = true
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
                    binding.btnCancelTransaction.text = getString(R.string.text_cancel_transaction)
                    binding.btnCancelTransaction.isEnabled = true
                }

                paymentStatus.equals("settlement", ignoreCase = true) -> {
                    binding.btnCancelTransaction.text = getString(R.string.text_payment_complete)
                    binding.btnCancelTransaction.isEnabled = false
                }

                paymentStatus.equals("expired", ignoreCase = true) -> {
                    binding.btnCancelTransaction.text = getString(R.string.text_payment_cancelled)
                    binding.btnCancelTransaction.isEnabled = false
                }
            }
        }
    }
}
