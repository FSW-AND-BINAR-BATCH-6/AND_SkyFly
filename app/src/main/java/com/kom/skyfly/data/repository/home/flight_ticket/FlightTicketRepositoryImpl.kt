package com.kom.skyfly.data.repository.home.flight_ticket

import com.kom.skyfly.data.datasource.home.HomeDataSource
import com.kom.skyfly.data.mapper.home.toFlightDetailTicket
import com.kom.skyfly.data.mapper.home.toFlightTickets
import com.kom.skyfly.data.model.home.filter.Filter
import com.kom.skyfly.data.model.home.flight.FlightTicket
import com.kom.skyfly.data.model.home.flight_detail.FlightDetailTicket
import com.kom.skyfly.data.model.home.seat_class.SeatClassHome
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
        seatClass: String?,
        limit: Int?,
        returnDate: String?,
        arrivalDate: String?,
        adult: Int?,
        children: Int?,
        baby: Int?,
        sort: String?,
    ): Flow<ResultWrapper<List<FlightTicket?>>> {
        return proceedFlow {
            dataSource.getAllFlight(
                page = page,
                departureAirport = departureAirport,
                limit = limit,
                arrivalAirport = arrivalAirport,
                departureDate = departureDate,
                seatClass = seatClass,
                returnDate = returnDate,
                adult = adult,
                baby = baby,
                children = children,
                sort = sort,
            ).data.toFlightTickets()
        }
    }

    override fun getFilterData(): List<Filter> = dataSource.getFilterData()

    override fun getReturnTicket(
        search: String?,
        page: Int?,
        departureAirport: String?,
        arrivalAirport: String?,
        departureDate: String?,
        seatClass: String?,
        limit: Int?,
        returnDate: String?,
        arrivalDate: String?,
        adult: Int?,
        children: Int?,
        baby: Int?,
        sort: String?,
    ): Flow<ResultWrapper<List<FlightTicket?>>> {
        return proceedFlow {
            dataSource.getAllFlight(
                page = page ?: 0,
                departureAirport = departureAirport.orEmpty(),
                limit = limit,
                arrivalAirport = arrivalAirport!!,
                departureDate = departureDate.orEmpty(),
                seatClass = seatClass,
                returnDate = returnDate,
                adult = adult,
                baby = baby,
                children = children,
                sort = sort,
            ).returnFlight.toFlightTickets()
        }
    }

    override fun getSeatClassTicket(): List<SeatClassHome> = dataSource.getSeatClassData()

    override fun getDetailTicket(
        id: String,
        seatClass: String?,
    ): Flow<ResultWrapper<FlightDetailTicket>> {
        return proceedFlow {
            dataSource.getDetailFlight(
                id = id,
                seatClass = seatClass,
            ).data.toFlightDetailTicket()
        }
    }
}
