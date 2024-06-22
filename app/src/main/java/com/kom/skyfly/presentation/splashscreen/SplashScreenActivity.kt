package com.kom.skyfly.presentation.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kom.skyfly.databinding.ActivitySplashScreenBinding
import com.kom.skyfly.presentation.onboarding.OnBoardingActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private val binding: ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        delayOnBoarding()
    }

    private fun delayOnBoarding() {
        lifecycleScope.launch {
            delay(2000)
            navigateToOnBoarding()
        }
    }

    private fun navigateToOnBoarding() {
        val intent =
            Intent(this, OnBoardingActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        startActivity(intent)
    }
}
