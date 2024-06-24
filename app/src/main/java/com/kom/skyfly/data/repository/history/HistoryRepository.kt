package com.kom.skyfly.data.repository.history

import com.kom.skyfly.data.datasource.history.HistoryDataSource
import com.kom.skyfly.data.mapper.toHistoryDomain
import com.kom.skyfly.data.model.history.new.HistoryDomain
import com.kom.skyfly.utils.ResultWrapper
import com.kom.skyfly.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getHistoryData(): Flow<ResultWrapper<HistoryDomain>>

//    fun getDetailHistoryById(id: Int): Flow<ResultWrapper<HistoryDetail>>
}

class HistoryRepositoryImpl(private val historyDataSource: HistoryDataSource) : HistoryRepository {
    override fun getHistoryData(): Flow<ResultWrapper<HistoryDomain>> {
        return proceedFlow {
            historyDataSource.getHistoryData().toHistoryDomain()
        }
    }
}

//    override fun getDetailHistoryById(id: Int): Flow<ResultWrapper<HistoryDetail>> {
//        return flow {
//            emit(ResultWrapper.Loading())
//            val historyDetail = historyDataSource.getHistoryDetailById(id.toString())
//            if (historyDetail != null) {
//                emit(ResultWrapper.Success(historyDetail))
//            } else {
//                emit(ResultWrapper.Error(Exception("History detail not found")))
//            }
//        }
//    }
