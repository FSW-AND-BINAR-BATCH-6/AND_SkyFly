package com.kom.skyfly.data.datasource.tickets

import com.kom.skyfly.data.source.network.model.tickets.TicketsResponse
import com.kom.skyfly.data.source.network.services.SkyFlyApiService

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface TicketsDataSource {
    suspend fun getTicketById(id: String): TicketsResponse
}

class TicketsDataSourceImpl(private val service: SkyFlyApiService) :
    TicketsDataSource {
    override suspend fun getTicketById(id: String): TicketsResponse {
        return service.getTicketsById(id)
    }
}
