package com.kom.skyfly.presentation.forgetpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.auth.AuthRepository
import kotlinx.coroutines.Dispatchers

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class ForgetPasswordViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun forgetPassword(email: String) = authRepository.forgetPassword(email).asLiveData(Dispatchers.IO)
}
