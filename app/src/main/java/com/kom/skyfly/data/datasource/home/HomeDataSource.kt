package com.kom.skyfly.data.datasource.home

import com.kom.skyfly.data.source.network.model.home.airport.AirportResponse

interface HomeDataSource {
    suspend fun getAllAirports(
        page: Int,
        limit: Int,
    ): AirportResponse
}
