package com.kom.skyfly.presentation.checkout.chooseseat

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kom.skyfly.R
import com.kom.skyfly.data.model.flightseat.FlightSeat
import com.kom.skyfly.databinding.ActivityChooseSeatBinding
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

    //    private var seats = (
//            "/AAA_AAA" +
//                    "/ARR_A"
//            )
    private var title = listOf<String>()

    private lateinit var seatBookView: SeatBookView
    private var seatTotal: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setTitleHeader()
        val flightId = "clx7whu6u001qt1jz9x8ig4ca"

        chooseSeatViewModel.getAllFlightSeat(flightId).observe(this) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    val seatData = result.payload
                    seatData?.let { seatList ->
                        seats = mapSeats(seatList)
                        title = generateTitle(seatList)
                        seatTotal = seatData.size
                        Log.d("ChooseSeatActivity", "Seats: $seats")
                        Log.d("ChooseSeatActivity", "Title: $title")

                        for (seat in seatList) {
                            val seatNumber = seat.seatNumber
                            val status = seat.status
                            val type = seat.type
                            Log.d(
                                "SeatInfo",
                                "Seat Number: $seatNumber, Status: $status, Type: $type",
                            )
                        }
                    }
                    setSeatView()
                },
                doOnError = { error ->
                    Log.e("ChooseSeatActivity", "Error: ${error.exception?.message}")
                },
            )
        }
    }

    private fun setSeatView() {
        seatBookView = binding.layoutSeat
        seatBookView.setSeatsLayoutString(seats)
            .isCustomTitle(true)
            .setCustomTitle(title)
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

    private fun mapSeats(seatData: List<FlightSeat>): String {
        val rows = mutableListOf<String>()
        var rowBuilder = StringBuilder("/")

        for ((index, seatStatus) in seatData.withIndex()) {
            rowBuilder.append(mapSeatStatus(seatStatus.status))

            if ((index + 1) % 3 == 0) {
                rowBuilder.append("_")
            }
            if ((index + 1) % 6 == 0) {
                rowBuilder.setLength(rowBuilder.length - 1)
                rows.add(rowBuilder.toString())
                rowBuilder = StringBuilder("/")
            }
        }

        if (rowBuilder.length > 1) {
            rowBuilder.setLength(rowBuilder.length - 1)
            rows.add(rowBuilder.toString())
        }

        return rows.joinToString(separator = "+")
    }

    private fun mapSeatStatus(seat: String?): Char {
        return when (seat) {
            "AVAILABLE" -> 'A'
            "OCCUPIED" -> 'U'
            "BOOKED" -> 'R'
            else -> ' '
        }
    }

    private fun generateTitle(seatData: List<FlightSeat>): List<String> {
        val titleList = mutableListOf<String>()
        titleList.add("/")
        var count = 1

        for (seat in seatData) {
            seat.seatNumber?.let { titleList.add(it) }
            count++

            if (count % 3 == 0 && count % 6 != 0) {
                titleList.add(" ")
                count = 3
            }

            if (count % 6 == 0) {
                titleList.add("/")
            }
        }

        return titleList
    }

    private fun isSeatAndTitleSizeValid(
        seats: String,
        title: List<String>,
    ): Boolean {
        val seatRows = seats.split("+")
        val expectedTitleSize = seatRows.size

        Log.d("ChooseSeatActivity", "Seat rows size: ${seatRows.size}")
        Log.d("ChooseSeatActivity", "Expected title size: $expectedTitleSize")
        Log.d("ChooseSeatActivity", "Actual title size: ${title.size}")

        return title.size == expectedTitleSize
    }
}
