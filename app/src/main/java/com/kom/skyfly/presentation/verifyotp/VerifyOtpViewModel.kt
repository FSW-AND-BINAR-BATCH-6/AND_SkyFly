package com.kom.skyfly.presentation.verifyotp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.auth.AuthRepository
import kotlinx.coroutines.Dispatchers

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class VerifyOtpViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun verifyOtp(
        token: String,
        otp: String,
    ) = authRepository.doVerifyAccount(token, otp).asLiveData(Dispatchers.IO)

    fun resendOtpRequest(token: String) = authRepository.resendOtpRequest(token).asLiveData(Dispatchers.IO)

    fun resendOtpSmsRequest(
        token: String,
        phoneNumber: String,
    ) = authRepository.resendOtpSmsRequest(token, phoneNumber).asLiveData(Dispatchers.IO)
}
