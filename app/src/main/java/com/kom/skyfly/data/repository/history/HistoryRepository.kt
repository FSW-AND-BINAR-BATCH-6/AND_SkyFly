package com.kom.skyfly.data.repository.history

import com.kom.skyfly.data.datasource.history.HistoryDataSource
import com.kom.skyfly.data.mapper.toHistoryDomain
import com.kom.skyfly.data.model.history.new.HistoryDomain
import com.kom.skyfly.utils.ResultWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
        return flow {
            emit(ResultWrapper.Loading())
            delay(1000)
            val result =
                historyDataSource.getHistoryData(limit, startDate, endDate, flightCode)
                    .toHistoryDomain()
            emit(ResultWrapper.Success(result))
        }
    }
}
