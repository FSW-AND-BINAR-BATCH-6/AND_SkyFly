package com.kom.skyfly.presentation.home.search_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.home.flight_ticket.FlightTicketRepository
import kotlinx.coroutines.Dispatchers

class SearchResultViewModel(
    private val flightRepository: FlightTicketRepository,
) : ViewModel(){
    fun getAllFlightTicket(
        search: String?,
        page: Int?,
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String,
        arrivalDate: String
    ) =
        flightRepository.getAllTicket(
            search = search, page = page, departureAirport = departureAirport, arrivalAirport = arrivalAirport, departureDate = departureDate, arrivalDate = arrivalDate
        ).asLiveData(Dispatchers.IO)
}
