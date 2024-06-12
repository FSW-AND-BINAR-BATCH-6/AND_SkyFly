package com.kom.skyfly.presentation.history.flightdetailhistory

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kom.skyfly.R
import com.kom.skyfly.data.model.history.Data
import com.kom.skyfly.databinding.ActivityFlightDetailHistoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FlightDetailHistoryActivity : AppCompatActivity() {
    // private lateinit var currentHistory: HistoryDetail

    private val binding: ActivityFlightDetailHistoryBinding by lazy {
        ActivityFlightDetailHistoryBinding.inflate(layoutInflater)
    }

    private val detailHistoryViewModel: FlightDetailHistoryViewModel by viewModel {
        parametersOf(intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val historyId = intent.getStringExtra(EXTRAS_MOVIE)
//        if (historyId != null) {
//           setupBind(historyId)
//        } else {
//            // Handle jika bookingCode tidak tersedia
//        }
        detailHistoryViewModel.history?.let { setupBind(it) }

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        const val EXTRAS_HISTORY = "EXTRAS_HISTORY"

        fun startActivity(
            context: Context,
            data: Data,
        ) {
            val intent = Intent(context, FlightDetailHistoryActivity::class.java)
            intent.putExtra(EXTRAS_HISTORY, data)
            context.startActivity(intent)
        }
    }

//    private fun observeData(historyId: String) {
//        detailHistoryViewModel.getDetailHistoryById(historyId).observe(this) { result ->
//            result.proceedWhen(
//                doOnSuccess = {
//                    it.payload?.let { historyDetail ->
//                        currentHistory = historyDetail
//                        setupBind(historyDetail)
//                    }
//                },
//            )
//        }
//    }

    private fun setupBind(historyDetail: Data) {
        with(binding.layoutFlightDetails) {
            tvPaymentStatus.text = historyDetail.status
            tvDetailDepartureDate.text = historyDetail.departureDate
            tvDetailDepartureTime.text = historyDetail.departureTime
            tvDetailArrivalDate.text = historyDetail.arrivalDate
            tvDetailArrivalTime.text = historyDetail.arrivalTime
            tvDetailBookingCode.text = historyDetail.bookingCode
            tvDetailClass.text = historyDetail.flightClass
            tvTotalPrice.text = historyDetail.total

            when (historyDetail.status) {
                "Unpaid" -> {
                    tvPaymentStatus.background =
                        ContextCompat.getDrawable(tvPaymentStatus.context, R.drawable.btn_rounded)
                    tvPaymentStatus.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                tvPaymentStatus.context,
                                R.color.red,
                            ),
                        )
                }

                "Cancelled" -> {
                    tvPaymentStatus.background =
                        ContextCompat.getDrawable(tvPaymentStatus.context, R.drawable.btn_rounded)
                    tvPaymentStatus.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                tvPaymentStatus.context,
                                R.color.darkGrey,
                            ),
                        )
                }

                "Paid" -> {
                    tvPaymentStatus.background =
                        ContextCompat.getDrawable(tvPaymentStatus.context, R.drawable.btn_rounded)
                    tvPaymentStatus.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                tvPaymentStatus.context,
                                R.color.green,
                            ),
                        )
                }

                else -> {
                    tvPaymentStatus.background = null
                }
            }
        }
    }
}
