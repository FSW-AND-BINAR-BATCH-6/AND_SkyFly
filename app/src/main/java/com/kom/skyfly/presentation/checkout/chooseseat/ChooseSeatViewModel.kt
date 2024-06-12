package com.kom.skyfly.presentation.checkout.chooseseat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.flightseat.FlightSeatRepository
import kotlinx.coroutines.Dispatchers

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class ChooseSeatViewModel(private val flightSeatRepository: FlightSeatRepository) : ViewModel() {
    fun getAllFlightSeat(idFlight: String) = flightSeatRepository.getAllFlightSeat(idFlight).asLiveData(Dispatchers.IO)

    fun getFlightSeat(idFlight: String) = flightSeatRepository.getFlightSeat(idFlight).asLiveData(Dispatchers.IO)
}
