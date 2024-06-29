package com.kom.skyfly.data.datasource.history

import com.kom.skyfly.data.source.network.model.history.HistoryResponse
import com.kom.skyfly.data.source.network.services.SkyFlyApiService

interface HistoryDataSource {
    suspend fun getHistoryData(
        limit: Int?,
        startDate: String?,
        endDate: String?,
        flightCode: String?,
    ): HistoryResponse
}

class HistoryDataSourceImpl(
    private val service: SkyFlyApiService,
) : HistoryDataSource {
    override suspend fun getHistoryData(
        limit: Int?,
        startDate: String?,
        endDate: String?,
        flightCode: String?,
    ): HistoryResponse {
        return service.getAllTransactionHistory(limit, startDate, endDate, flightCode)
    }
}
