package com.kom.skyfly.data.datasource.flightseat

import com.kom.skyfly.data.source.network.model.flightseat.FlightSeatResponse
import com.kom.skyfly.data.source.network.services.SkyFlyApiService

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface FlightSeatDataSource {
    suspend fun getAllFlightSeat(id: String): FlightSeatResponse
}

class FlightSeatDataSourceImpl(private val service: SkyFlyApiService) : FlightSeatDataSource {
    override suspend fun getAllFlightSeat(id: String): FlightSeatResponse {
        return service.getAllFlightSeat(id)
    }
}
