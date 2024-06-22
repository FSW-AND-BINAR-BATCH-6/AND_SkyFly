package com.kom.skyfly.data.datasource.home

import com.kom.skyfly.data.source.network.model.home.airport.AirportResponse
import com.kom.skyfly.data.source.network.model.home.flight.FlightResponse
import com.kom.skyfly.data.source.network.model.home.flight_detail.FlightDetailResponse

interface HomeDataSource {
    suspend fun getAllAirports(): AirportResponse

    suspend fun getAllFlight(
        search: String? = null,
        page: Int,
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String,
        seatClass: String,
    ): FlightResponse

    suspend fun getDetailFlight(
        id: String,
        seatClass: String,
    ): FlightDetailResponse
}
