package com.kom.skyfly.data.datasource.home

import com.kom.skyfly.data.source.network.model.home.airport.AirportResponse
import com.kom.skyfly.data.source.network.model.home.flight.FlightResponse
import com.kom.skyfly.data.source.network.services.SkyFlyApiService

class HomeDataSourceImpl(
    private val service: SkyFlyApiService,
) : HomeDataSource {
    override suspend fun getAllAirports(
        page: Int,
        limit: Int,
    ): AirportResponse {
        return service.getAllAirports(page = page, limit = limit)
    }

    override suspend fun getAllFlight(
        search: String?,
        page: Int,
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String,
    ): FlightResponse {
        return service.getAllFlights(
            search = search,
            page = page,
            departureAirport = departureAirport,
            arrivalAirport = arrivalAirport,
            departureDate = departureDate,
        )
    }
}
