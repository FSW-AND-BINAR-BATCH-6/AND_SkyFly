package com.kom.skyfly.presentation.checkout.chooseseat

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kom.skyfly.R
import com.kom.skyfly.databinding.ActivityChooseSeatBinding
import dev.jahidhasanco.seatbookview.SeatBookView
import dev.jahidhasanco.seatbookview.SeatClickListener

class ChooseSeatActivity : AppCompatActivity() {
    private val binding: ActivityChooseSeatBinding by lazy {
        ActivityChooseSeatBinding.inflate(layoutInflater)
    }

    private var seats = (
        "/AAA_AAA" +
            "/ARR_AUU" +
            "/AAA_AAA" +
            "/AAA_AAA" +
            "/AAA_AAA" +
            "/AAA_AAA" +
            "/AAA_AAA" +
            "/AAA_AAA" +
            "/AAA_AAA" +
            "/AAA_AAA" +
            "/AAA_AAA" +
            "/AAA_AAA"
    )

    private var title =
        listOf(
            "/", "A1", "B1", "C1", "", "D1", "E1", "F1",
            "/", "A2", "B2", "C2", "", "D2", "E2", "F2",
            "/", "A3", "B3", "C3", "", "D3", "E3", "F3",
            "/", "A4", "B4", "C4", "", "D4", "E4", "F4",
            "/", "A5", "B5", "C5", "", "D5", "E5", "F5",
            "/", "A6", "B6", "C6", "", "D6", "E6", "F6",
            "/", "A7", "B7", "C7", "", "D7", "E7", "F7",
            "/", "A8", "B8", "C8", "", "D8", "E8", "F8",
            "/", "A9", "B9", "C9", "", "D9", "E9", "F9",
            "/", "A10", "B10", "C10", "", "D10", "E10", "F10",
            "/", "A11", "B11", "C11", "", "D11", "E11", "F11",
            "/", "A12", "B12", "C12", "", "D12", "E12", "F12",
        )

    private lateinit var seatBookView: SeatBookView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setTitleHeader()
        setSeatView()
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
                }

                override fun onBookedSeatClick(view: View) {
                }

                override fun onReservedSeatClick(view: View) {
                }
            },
        )
    }

    private fun setTitleHeader() {
        binding.layoutHeader.tvTitleHeader.text = getString(R.string.text_header_choose_seat)
    }
}
