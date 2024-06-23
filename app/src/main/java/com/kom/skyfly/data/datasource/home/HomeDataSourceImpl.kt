package com.kom.skyfly.data.datasource.home

import com.kom.skyfly.data.source.network.model.home.airport.AirportResponse
import com.kom.skyfly.data.source.network.model.home.flight.FlightResponse
import com.kom.skyfly.data.source.network.model.home.flight_detail.FlightDetailResponse
import com.kom.skyfly.data.source.network.services.SkyFlyApiService

class HomeDataSourceImpl(
    private val service: SkyFlyApiService,
) : HomeDataSource {
    override suspend fun getAllAirports(): AirportResponse {
        return service.getAllAirports()
    }

    override suspend fun getAllFlight(
        search: String?,
        page: Int,
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String,
        seatClass: String,
    ): FlightResponse {
        return service.getAllFlights(
            search = search,
            page = page,
            departureAirport = departureAirport,
            arrivalAirport = arrivalAirport,
            departureDate = departureDate,
            seatClass = seatClass,
        )
    }

    override suspend fun getDetailFlight(
        id: String,
        seatClass: String,
    ): FlightDetailResponse {
        return service.getDetailFlightById(id = id, seatClass = seatClass)
    }
}
