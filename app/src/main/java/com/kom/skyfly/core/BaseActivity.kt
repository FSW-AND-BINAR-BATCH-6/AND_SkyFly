package com.kom.skyfly.core

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.kom.skyfly.presentation.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
open class BaseActivity : AppCompatActivity() {
    private val baseViewModel: BaseViewModel by viewModel()

    fun handleUnAuthorize() {
        baseViewModel.clearSession()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
