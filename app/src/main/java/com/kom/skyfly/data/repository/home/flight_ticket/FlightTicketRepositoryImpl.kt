package com.kom.skyfly.data.repository.home.flight_ticket

import com.kom.skyfly.data.datasource.home.HomeDataSource
import com.kom.skyfly.data.mapper.home.toFlightTickets
import com.kom.skyfly.data.model.home.flight.FlightTicket
import com.kom.skyfly.utils.ResultWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FlightTicketRepositoryImpl(
    private val dataSource: HomeDataSource,
) : FlightTicketRepository {
    override fun getAllTicket(
        search: String?,
        page: Int,
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String,
    ): Flow<ResultWrapper<List<FlightTicket>>> {
        return flow {
            emit(ResultWrapper.Loading())
            val result =
                dataSource.getAllFlight(
                    search = search,
                    page = page,
                    departureAirport = departureAirport,
                    arrivalAirport = arrivalAirport,
                    departureDate = departureDate,
                ).data.toFlightTickets()
            delay(500)
            emit(ResultWrapper.Success(result))
        }
    }
}
