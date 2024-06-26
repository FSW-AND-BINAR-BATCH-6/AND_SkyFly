package com.kom.skyfly.data.repository.searchhistory

import com.kom.skyfly.data.datasource.searchhistory.SearchHistoryDataSource
import com.kom.skyfly.data.mapper.toSearchHistoryEntity
import com.kom.skyfly.data.mapper.toSearchHistoryList
import com.kom.skyfly.data.model.searchhistory.SearchHistory
import com.kom.skyfly.data.source.local.database.entity.SearchHistoryEntity
import com.kom.skyfly.utils.ResultWrapper
import com.kom.skyfly.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface SearchHistoryRepository {
    fun createSearchHistory(
        userId: String,
        searchHistory: String,
    ): Flow<ResultWrapper<Boolean>>

    fun deleteSearchHistory(item: SearchHistory): Flow<ResultWrapper<Boolean>>

    fun deleteAllSearchHistory(): Flow<ResultWrapper<Unit>>

    fun getUserSearchHistory(): Flow<ResultWrapper<List<SearchHistory>>>
}

class SearchHistoryRepositoryImpl(private val searchHistoryDataSource: SearchHistoryDataSource) :
    SearchHistoryRepository {
    override fun createSearchHistory(
        userId: String,
        searchHistory: String,
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val affectedRows =
                searchHistoryDataSource.insertSearchHistory(
                    SearchHistoryEntity(
                        userId = userId,
                        searchHistory = searchHistory,
                    ),
                )
            affectedRows > 0
        }
    }

    override fun deleteSearchHistory(item: SearchHistory): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { searchHistoryDataSource.deleteSearchHistory(item.toSearchHistoryEntity()) > 0 }
    }

    override fun deleteAllSearchHistory(): Flow<ResultWrapper<Unit>> {
        return proceedFlow { searchHistoryDataSource.deleteAllSearchHistory() }
    }

    override fun getUserSearchHistory(): Flow<ResultWrapper<List<SearchHistory>>> {
        return searchHistoryDataSource.getAllSearchHistory()
            .map { searchHistoryEntities ->
                val searchHistoryList =
                    searchHistoryEntities.toSearchHistoryList()
                if (searchHistoryList != null) {
                    ResultWrapper.Success(searchHistoryList)
                } else {
                    ResultWrapper.Empty()
                }
            }.catch { e ->
                emit(ResultWrapper.Error(Exception(e)))
            }.onStart {
                emit(ResultWrapper.Loading())
            }
    }
}
