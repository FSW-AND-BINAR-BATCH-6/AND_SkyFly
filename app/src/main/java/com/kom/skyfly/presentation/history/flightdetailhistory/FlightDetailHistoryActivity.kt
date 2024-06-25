package com.kom.skyfly.presentation.history.flightdetailhistory

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.kom.skyfly.R
import com.kom.skyfly.data.model.transaction.detail.TransactionDetailResponses
import com.kom.skyfly.databinding.ActivityFlightDetailHistoryBinding
import com.kom.skyfly.utils.formatToRupiah
import com.kom.skyfly.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class FlightDetailHistoryActivity : AppCompatActivity() {
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
                            setupBind(it.payload)
                        }, 2000)
                    },
                    doOnLoading = {
                        binding.shimmerDetailHistory.isVisible = true
                        binding.svFlightDetails.isVisible = false
                    },
                    doOnError = { error ->
                        Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT)
                            .show()
                    },
                    doOnEmpty = {
                        binding.shimmerDetailHistory.isVisible = false
                        getString(R.string.text_havent_made_a_booking)
                        binding.svFlightDetails.isVisible = false
                    },
                )
            }
        }
    }

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
                    R.string.tv_strip,
                    historyDetail?.data?.transactionDetails?.first()?.flight?.departureAirport!!.name,
                )
            tvDetailTerminal.text =
                historyDetail.data.transactionDetails.first()?.flight!!.airline.terminal
            tvDetailAirline.text =
                getString(
                    R.string.tv_strip,
                    historyDetail.data.transactionDetails.first()!!.flight.airline.name,
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
                tvTotalByAgeGroupAdult.text = "$adultCount Adult"
                tvTotalPriceByAgeGroupAdult.text = totalPricePassengerAdult.formatToRupiah().toString()
                tvTotalByAgeGroupAdult.isVisible = true
                tvTotalPriceByAgeGroupAdult.isVisible = true
            } else {
                tvTotalByAgeGroupAdult.isVisible = false
                tvTotalPriceByAgeGroupAdult.isVisible = false
            }

            if (childCount > 0) {
                tvTotalByAgeGroupChild.text = "$childCount Child"
                tvTotalPriceByAgeGroupChild.text = totalPricePassengerChild.formatToRupiah().toString()
                tvTotalByAgeGroupChild.isVisible = true
                tvTotalPriceByAgeGroupChild.isVisible = true
            } else {
                tvTotalByAgeGroupChild.isVisible = false
                tvTotalPriceByAgeGroupChild.isVisible = false
            }

            if (babyCount > 0) {
                tvTotalByAgeGroupBaby.text = "$babyCount Baby"
                tvTotalPriceByAgeGroupBaby.text = totalPricePassengerBaby.formatToRupiah().toString()
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
