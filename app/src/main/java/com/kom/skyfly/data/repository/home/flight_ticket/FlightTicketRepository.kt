package com.kom.skyfly.data.repository.home.flight_ticket

import com.kom.skyfly.data.model.home.flight.FlightTicket
import com.kom.skyfly.data.model.home.flight_detail.FlightDetailTicket
import com.kom.skyfly.data.model.home.seat_class.SeatClassHome
import com.kom.skyfly.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface FlightTicketRepository {
    fun getAllTicket(
        search: String? = null,
        page: Int,
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String,
        seatClass: String?,
        limit: Int? = 20,
        returnDate: String? = null,
        arrivalDate: String? = null,
        adult: Int? = 1,
        children: Int? = 0,
        baby: Int? = 0,
    ): Flow<ResultWrapper<List<FlightTicket?>>>

    fun getReturnTicket(
        search: String? = null,
        page: Int? = null,
        departureAirport: String? = null,
        arrivalAirport: String? = null,
        departureDate: String? = null,
        seatClass: String? = null,
        limit: Int? = 10000,
        returnDate: String? = null,
        arrivalDate: String? = null,
        adult: Int? = 1,
        children: Int? = 0,
        baby: Int? = 0,
    ): Flow<ResultWrapper<List<FlightTicket?>>>

    fun getDetailTicket(
        id: String,
        seatClass: String?,
    ): Flow<ResultWrapper<FlightDetailTicket>>

    fun getSeatClassTicket(): List<SeatClassHome>
}
