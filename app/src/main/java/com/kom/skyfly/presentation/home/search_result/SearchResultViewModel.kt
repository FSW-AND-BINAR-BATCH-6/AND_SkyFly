package com.kom.skyfly.presentation.home.search_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.home.flight_ticket.FlightTicketRepository
import kotlinx.coroutines.Dispatchers

class SearchResultViewModel(
//    private val extras: Bundle?,
    private val flightRepository: FlightTicketRepository,
) : ViewModel() {
    fun getAllFlightTicket(
        search: String? = null,
        page: Int,
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String,
        seatClass: String,
    ) = flightRepository.getAllTicket(
        search = search,
        page = page,
        departureAirport = departureAirport,
        arrivalAirport = arrivalAirport,
        departureDate = departureDate,
        seatClass = seatClass,
    ).asLiveData(Dispatchers.IO)
}
