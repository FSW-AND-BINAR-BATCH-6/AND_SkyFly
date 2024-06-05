package com.kom.skyfly.presentation.onboarding

import androidx.lifecycle.ViewModel
import com.kom.skyfly.data.repository.userpref.UserPrefRepository

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class OnBoardingViewModel(private val userPrefRepository: UserPrefRepository) : ViewModel() {
    fun isOnBoardingShow() = userPrefRepository.isOnBoardingShow()
}
