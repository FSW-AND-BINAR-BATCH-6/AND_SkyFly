package com.kom.skyfly.data.repository.home.flight_ticket

import com.kom.skyfly.data.model.home.flight.FlightTicket
import com.kom.skyfly.data.model.home.flight_detail.FlightDetailTicket
import com.kom.skyfly.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface FlightTicketRepository {
    fun getAllTicket(
        search: String? = null,
        page: Int,
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String,
        seatClass: String,
    ): Flow<ResultWrapper<List<FlightTicket?>>>

    fun getDetailTicket(
        id: String,
        seatClass: String,
    ): Flow<ResultWrapper<FlightDetailTicket>>
}
