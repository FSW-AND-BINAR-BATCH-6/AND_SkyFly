package com.kom.skyfly.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.auth.AuthRepository
import kotlinx.coroutines.Dispatchers

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun doRegister(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
    ) = authRepository
        .doRegister(
            fullName = fullName,
            email = email,
            phoneNumber = phoneNumber,
            password = password,
        )
        .asLiveData(Dispatchers.IO)
}
