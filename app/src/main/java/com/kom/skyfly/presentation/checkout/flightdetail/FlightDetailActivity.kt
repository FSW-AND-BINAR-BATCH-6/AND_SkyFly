package com.kom.skyfly.presentation.checkout.flightdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kom.skyfly.databinding.ActivityFlightDetailBinding

class FlightDetailActivity : AppCompatActivity() {
    private val binding: ActivityFlightDetailBinding by lazy {
        ActivityFlightDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
