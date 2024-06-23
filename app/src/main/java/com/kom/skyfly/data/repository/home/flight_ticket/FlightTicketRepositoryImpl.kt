package com.kom.skyfly.data.repository.home.flight_ticket

import com.kom.skyfly.data.datasource.home.HomeDataSource
import com.kom.skyfly.data.mapper.home.toFlightDetailTicket
import com.kom.skyfly.data.mapper.home.toFlightTickets
import com.kom.skyfly.data.model.home.flight.FlightTicket
import com.kom.skyfly.data.model.home.flight_detail.FlightDetailTicket
import com.kom.skyfly.utils.ResultWrapper
import com.kom.skyfly.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

class FlightTicketRepositoryImpl(
    private val dataSource: HomeDataSource,
) : FlightTicketRepository {
    override fun getAllTicket(
        search: String?,
        page: Int,
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String,
        seatClass: String,
    ): Flow<ResultWrapper<List<FlightTicket?>>> {
        return proceedFlow {
            dataSource.getAllFlight(
                page = page,
                departureAirport = departureAirport,
                arrivalAirport = arrivalAirport,
                departureDate = departureDate,
                seatClass = seatClass,
            ).data.toFlightTickets()
        }
    }

    override fun getDetailTicket(
        id: String,
        seatClass: String,
    ): Flow<ResultWrapper<FlightDetailTicket>> {
        return proceedFlow {
            dataSource.getDetailFlight(
                id = id,
                seatClass = seatClass,
            ).data.toFlightDetailTicket()
        }
    }
}
