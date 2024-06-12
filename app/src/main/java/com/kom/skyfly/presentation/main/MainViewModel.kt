package com.kom.skyfly.presentation.main

import androidx.lifecycle.ViewModel
import com.kom.skyfly.data.repository.userpref.UserPrefRepository

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class MainViewModel(
    private val userPrefRepository: UserPrefRepository,
) : ViewModel() {
    fun getUserToken() = userPrefRepository.getUserToken()
}
