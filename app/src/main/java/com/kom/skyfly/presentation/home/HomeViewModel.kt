package com.kom.skyfly.presentation.home

import androidx.lifecycle.ViewModel
import com.kom.skyfly.data.repository.userpref.UserPrefRepository

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class HomeViewModel(private val userPrefRepository: UserPrefRepository) : ViewModel() {
    fun setOnBoardingShow(isShown: Boolean) = userPrefRepository.setOnBoardingShow(isShown)
}
