package com.kom.skyfly.data.repository.history

import com.kom.skyfly.data.datasource.history.HistoryDataSource
import com.kom.skyfly.data.model.history.SectionedDate
import com.kom.skyfly.utils.ResultWrapper
import com.kom.skyfly.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getHistoryData(): Flow<ResultWrapper<List<SectionedDate>>>
}

class HistoryRepositoryImpl(private val historyDataSource: HistoryDataSource) :
    HistoryRepository {
    override fun getHistoryData(): Flow<ResultWrapper<List<SectionedDate>>> {
        return proceedFlow { historyDataSource.getHistoryData() }
    }
}
