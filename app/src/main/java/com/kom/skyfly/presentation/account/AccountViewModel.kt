package com.kom.skyfly.presentation.account

import androidx.lifecycle.ViewModel
import com.kom.skyfly.data.repository.userpref.UserPrefRepository

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class AccountViewModel(private val userPrefRepository: UserPrefRepository) : ViewModel() {
    fun doLogout(token: String?) =
        userPrefRepository
            .saveUserToken(token)
}
