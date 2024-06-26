package com.kom.skyfly.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.home.airport.AirportRepository
import com.kom.skyfly.data.repository.home.destination_favourite.DestinationFavouriteRepository
import com.kom.skyfly.data.repository.home.flight_ticket.FlightTicketRepository
import com.kom.skyfly.data.repository.userpref.UserPrefRepository
import kotlinx.coroutines.Dispatchers

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class HomeViewModel(
//    private val extras: Bundle?,
    private val userPrefRepository: UserPrefRepository,
    private val airportRepository: AirportRepository,
    private val flightRepository: FlightTicketRepository,
    private val destinationFavoriteRepository: DestinationFavouriteRepository,
) : ViewModel() {
//    val destination = extras?.getParcelable<Airport>(HomeFragment.EXTRAS_DESTINATION)

    fun setOnBoardingShow(isShown: Boolean) = userPrefRepository.setOnBoardingShow(isShown)

    fun getAllDestinationFavorite() =
        destinationFavoriteRepository.getAllDestinationFavourite().asLiveData(
            Dispatchers.IO,
        )
}
