package com.kom.skyfly.presentation.flightdetails

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kom.skyfly.R
import com.kom.skyfly.databinding.ActivityFlightDetailsBinding
import com.kom.skyfly.databinding.ActivityMainBinding

class FlightDetailsActivity : AppCompatActivity() {

    private val binding: ActivityFlightDetailsBinding by lazy {
        ActivityFlightDetailsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
