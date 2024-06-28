package com.kom.skyfly.data.repository.history

import com.kom.skyfly.data.datasource.history.HistoryDataSource
import com.kom.skyfly.data.mapper.toHistoryDomain
import com.kom.skyfly.data.model.history.new.HistoryDomain
import com.kom.skyfly.utils.ResultWrapper
import com.kom.skyfly.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getHistoryData(
        limit: Int?,
        startDate: String?,
        endDate: String?,
        flightCode: String?,
    ): Flow<ResultWrapper<HistoryDomain>>
}

class HistoryRepositoryImpl(private val historyDataSource: HistoryDataSource) : HistoryRepository {
    override fun getHistoryData(
        limit: Int?,
        startDate: String?,
        endDate: String?,
        flightCode: String?,
    ): Flow<ResultWrapper<HistoryDomain>> {
        return proceedFlow {
            historyDataSource.getHistoryData(limit, startDate, endDate, flightCode)
                .toHistoryDomain()
        }
    }
}
