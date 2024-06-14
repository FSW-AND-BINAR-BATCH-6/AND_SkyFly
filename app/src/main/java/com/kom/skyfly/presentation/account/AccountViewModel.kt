package com.kom.skyfly.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.auth.AuthRepository
import com.kom.skyfly.data.repository.profiles.ProfileRepository
import com.kom.skyfly.data.repository.userpref.UserPrefRepository
import kotlinx.coroutines.Dispatchers

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class AccountViewModel(
    private val userPrefRepository: UserPrefRepository,
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    fun doLogout(token: String?) =
        userPrefRepository
            .saveUserToken(token)

    fun getProfile() =
        profileRepository
            .getProfile()
            .asLiveData(Dispatchers.IO)

    fun forgetPassword(email: String) =
        authRepository
            .forgetPassword(email)
            .asLiveData(Dispatchers.IO)

    fun isUserLoggedIn() = authRepository.isUserLoggedIn().asLiveData(Dispatchers.IO)
}
