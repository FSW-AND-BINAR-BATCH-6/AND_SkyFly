package com.kom.skyfly.presentation.home

import androidx.lifecycle.ViewModel
import com.kom.skyfly.data.repository.home.AirportRepository
import com.kom.skyfly.data.repository.userpref.UserPrefRepository

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class HomeViewModel(
//    private val extras: Bundle?,
    private val userPrefRepository: UserPrefRepository,
    private val airportRepository: AirportRepository,
) : ViewModel() {
//    val destination = extras?.getParcelable<Airport>(HomeFragment.EXTRAS_DESTINATION)

    fun setOnBoardingShow(isShown: Boolean) = userPrefRepository.setOnBoardingShow(isShown)
}
