package com.kom.skyfly.presentation.home

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.kom.skyfly.data.model.home.search.Airport
import com.kom.skyfly.data.repository.userpref.UserPrefRepository

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class HomeViewModel(
    private val userPrefRepository: UserPrefRepository,
    private val extras: Bundle?
) : ViewModel() {
    val destination = extras?.getParcelable<Airport>(HomeFragment.EXTRAS_DESTINATION)
    fun setOnBoardingShow(isShown: Boolean) = userPrefRepository.setOnBoardingShow(isShown)
}
