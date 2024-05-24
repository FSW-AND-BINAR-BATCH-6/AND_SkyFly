package com.kom.skyfly.presentation.flightdetailhistory

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
    }
}
