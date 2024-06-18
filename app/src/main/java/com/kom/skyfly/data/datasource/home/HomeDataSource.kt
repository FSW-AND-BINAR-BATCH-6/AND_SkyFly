package com.kom.skyfly.data.datasource.home

import com.kom.skyfly.data.source.network.model.home.airport.AirportResponse
import com.kom.skyfly.data.source.network.model.home.flight.FlightResponse

interface HomeDataSource {
    suspend fun getAllAirports(
        page: Int,
        limit: Int,
    ): AirportResponse

    suspend fun getAllFlight(
        search: String? = null,
        page: Int? = 1,
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String,
        arrivalDate: String? = null
    ) : FlightResponse
}
