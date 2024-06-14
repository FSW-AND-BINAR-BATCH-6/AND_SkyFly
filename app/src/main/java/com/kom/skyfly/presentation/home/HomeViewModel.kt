package com.kom.skyfly.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.destinationfavorite.DestinationFavoriteRepository
import com.kom.skyfly.data.repository.userpref.UserPrefRepository
import kotlinx.coroutines.Dispatchers

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class HomeViewModel(
    private val userPrefRepository: UserPrefRepository,
    private val destinationFavoriteRepository: DestinationFavoriteRepository,
//    private val extras: Bundle?,
) : ViewModel() {
//    val destination = extras?.getParcelable<Airport>(HomeFragment.EXTRAS_DESTINATION)

    fun setOnBoardingShow(isShown: Boolean) = userPrefRepository.setOnBoardingShow(isShown)

    fun getAllDestinationFavorite() = destinationFavoriteRepository.getAllDestinationFavorite().asLiveData(Dispatchers.IO)
}
