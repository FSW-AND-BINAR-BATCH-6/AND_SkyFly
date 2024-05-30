package com.kom.skyfly.presentation.history.flightdetailhistory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kom.skyfly.databinding.ActivityFlightDetailHistoryBinding

class FlightDetailHistoryActivity : AppCompatActivity() {
    private val binding: ActivityFlightDetailHistoryBinding by lazy {
        ActivityFlightDetailHistoryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setOnClickListeners()

        /*val flightId = intent.getStringExtra("flight_id")
        if (flightId != null) {
            val flightData =

                binding.layoutFlightDetails.tvDetailBookingCode.text = flightData.bookingCode
            binding.tvDetailDepartureDate.text = flightData.departureDate
            binding.tvDetailDepartureAirport.text = flightData.departureAirport
        } else {
            // Jika flightId tidak tersedia, Anda dapat menangani kasus ini sesuai kebutuhan aplikasi Anda
        }*/
    }

    private fun setOnClickListeners() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}
