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
        limit: Int? = 20,
        arrivalDate: String? = null,
        adult: Int? = 1,
        children: Int? = 0,
        baby: Int? = 0,
    ) = flightRepository.getAllTicket(
        search = search,
        page = page,
        departureAirport = departureAirport,
        arrivalAirport = arrivalAirport,
        departureDate = departureDate,
        seatClass = seatClass,
        limit = limit,
        arrivalDate = arrivalDate,
        adult = adult,
        children = children,
        baby = baby,
    ).asLiveData(Dispatchers.IO)
}
