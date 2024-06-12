package com.kom.skyfly.data.repository.history

import com.kom.skyfly.data.datasource.history.HistoryDataSource
import com.kom.skyfly.data.model.history.SectionedDate
import com.kom.skyfly.utils.ResultWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface HistoryRepository {
    fun getHistoryData(): Flow<ResultWrapper<List<SectionedDate>>>

//    fun getDetailHistoryById(id: Int): Flow<ResultWrapper<HistoryDetail>>
}

class HistoryRepositoryImpl(private val historyDataSource: HistoryDataSource) :
    HistoryRepository {
    override fun getHistoryData(): Flow<ResultWrapper<List<SectionedDate>>> {
        return flow {
            emit(ResultWrapper.Loading())
            delay(2000)
            val result = historyDataSource.getHistoryData()
            emit(ResultWrapper.Success(result))
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
}
