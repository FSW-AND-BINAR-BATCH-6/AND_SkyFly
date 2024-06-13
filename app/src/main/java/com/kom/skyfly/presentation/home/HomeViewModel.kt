package com.kom.skyfly.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kom.skyfly.data.model.home.search.Airport
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

    private val _destination = MutableLiveData<Airport?>()
    val destination: LiveData<Airport?> get() = _destination

    fun setDestination(value: Airport?) {
        _destination.value = value
    }

    fun setOnBoardingShow(isShown: Boolean) = userPrefRepository.setOnBoardingShow(isShown)
}
