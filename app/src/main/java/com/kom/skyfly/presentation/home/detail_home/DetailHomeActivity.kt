package com.kom.skyfly.presentation.home.detail_home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kom.skyfly.R
import com.kom.skyfly.databinding.ActivityDetailHomeBinding

class DetailHomeActivity : AppCompatActivity() {
    private val binding : ActivityDetailHomeBinding by lazy {
        ActivityDetailHomeBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickListener()
    }

    private fun setOnClickListener() {

    }
}
