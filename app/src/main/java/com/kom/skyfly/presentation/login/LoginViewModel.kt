package com.kom.skyfly.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.auth.AuthRepository
import com.kom.skyfly.data.repository.userpref.UserPrefRepository
import kotlinx.coroutines.Dispatchers

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userPrefRepository: UserPrefRepository,
) : ViewModel() {
    fun doLogin(
        email: String,
        password: String,
    ) = authRepository
        .doLogin(email, password)
        .asLiveData(Dispatchers.IO)

    fun saveUserToken(token: String?) =
        userPrefRepository
            .saveUserToken(token)
}
