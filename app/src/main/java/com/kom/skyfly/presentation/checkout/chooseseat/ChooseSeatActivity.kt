package com.kom.skyfly.presentation.checkout.chooseseat

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.kom.skyfly.R
import com.kom.skyfly.databinding.ActivityChooseSeatBinding
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.utils.NoInternetException
import com.kom.skyfly.utils.proceedWhen
import dev.jahidhasanco.seatbookview.SeatBookView
import dev.jahidhasanco.seatbookview.SeatClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseSeatActivity : AppCompatActivity() {
    private val binding: ActivityChooseSeatBinding by lazy {
        ActivityChooseSeatBinding.inflate(layoutInflater)
    }
    private val chooseSeatViewModel: ChooseSeatViewModel by viewModel()

    private var seats: String = ""
    private var titles = listOf<String>()

    private lateinit var seatBookView: SeatBookView
    private var seatTotal: Int = 0
    private val seatType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setTitleHeader()
        val flightId = "clxelwz45001nhwic6jpydgb8"
        getAllFlightSeatData(flightId)
        getFlightSeatData(flightId)
    }

    private fun getFlightSeatData(flightId: String) {
        chooseSeatViewModel.getFlightSeat(flightId).observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = { resultWrapper ->
                    val payload = resultWrapper.payload

                    if (payload != null) {
                        val (status, title) = payload
                        seats = status
                        titles = title
                        seatTotal = title.size
                        setSeatView()
                    } else {
                        Log.e("ChooseSeatActivity", "Error: Payload is null")
                    }
                    binding.shmProgressSeatView.isVisible = false
                    binding.csvSeatView.setState(ContentState.SUCCESS)
                },
                doOnEmpty = {
                    binding.shmProgressSeatView.isVisible = false
                    binding.csvSeatView.setState(ContentState.EMPTY, "Seat kosong!")
                },
                doOnError = { error ->
                    binding.shmProgressSeatView.isVisible = false
                    if (error.exception is NoInternetException) {
                        binding.csvSeatView.setState(ContentState.ERROR_NETWORK)
                    } else {
                        binding.csvSeatView.setState(ContentState.ERROR_GENERAL)
                    }
                    Log.e("ChooseSeatActivity", "Error: ${error.exception?.message}")
                },
                doOnLoading = {
                    binding.shmProgressSeatView.isVisible = true
                },
            )
        }
    }

    private fun getAllFlightSeatData(flightId: String) {
        chooseSeatViewModel.getAllFlightSeat(flightId).observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = { resultWrapper ->
                    val payload = resultWrapper.payload
                    Log.d("SeatDatas", "getAllFlightSeatData: $payload")
                    payload.let {
                        val totalItems = it?.totalItems
                        seatTotal = totalItems ?: 0
                        binding.tvSeatTitle.text = "EKONOMI - $seatTotal Kursi"
                    }
                },
                doOnLoading = {
                    binding.tvSeatTitle.text = "Loading..."
                },
                doOnEmpty = {
                    binding.tvSeatTitle.text = "SeatView Kosong"
                },
                doOnError = {
                    binding.tvSeatTitle.text = "Error"
                },
            )
        }
    }

    private fun setSeatView() {
        seatBookView = binding.layoutSeat
        seatBookView.setSeatsLayoutString(seats)
            .isCustomTitle(true)
            .setCustomTitle(titles)
            .setSeatLayoutPadding(2)
            .setSeatSizeBySeatsColumnAndLayoutWidth(7, -1)

        seatBookView.show()
        seatBookView.setSeatClickListener(
            object : SeatClickListener {
                override fun onAvailableSeatClick(
                    selectedIdList: List<Int>,
                    view: View,
                ) {
                    Log.d("SeatClick", "Available seat clicked: $selectedIdList")
                }

                override fun onBookedSeatClick(view: View) {
                    Log.d("SeatClick", "Booked seat clicked")
                }

                override fun onReservedSeatClick(view: View) {
                    Log.d("SeatClick", "Reserved seat clicked")
                }
            },
        )
    }

    private fun setTitleHeader() {
        binding.layoutHeader.tvTitleHeader.text = getString(R.string.text_header_choose_seat)
    }
}
