package com.kom.skyfly.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kom.skyfly.R
import com.kom.skyfly.data.source.network.model.login.LoginRequest
import com.kom.skyfly.data.source.network.services.SkyFlyApiService
import com.kom.skyfly.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

    private fun getDataFromApi() {
        val apiService = SkyFlyApiService.invoke()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val loginRequest = LoginRequest("example@gmail.com", "example123")
                val response = apiService.login(loginRequest)
                Log.d("Coins", "Response: $response")
            } catch (e: Exception) {
                if (e is retrofit2.HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    Log.e("Coins Error", "HTTP Error: ${e.code()} - ${e.message()}\nBody: $errorBody", e)
                } else {
                    Log.e("Coins Error", "Error: ${e.message}", e)
                }
            }
        }
    }
}
