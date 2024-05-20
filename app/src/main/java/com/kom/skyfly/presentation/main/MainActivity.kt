package com.kom.skyfly.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kom.skyfly.R
import com.kom.skyfly.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setBottomNavbar()
    }

    private fun setBottomNavbar() {
        val navController = findNavController(R.id.nav_host)
        binding.navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, argumen ->
            when (destination.id) {
            }
        }
    }
}
