package com.kom.skyfly.data.datasource.home

import com.kom.skyfly.data.source.network.model.home.airport.AirportResponse
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
}
