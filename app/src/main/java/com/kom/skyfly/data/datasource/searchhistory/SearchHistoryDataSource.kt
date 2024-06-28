package com.kom.skyfly.data.datasource.searchhistory

import com.kom.skyfly.data.source.local.database.dao.SearchHistoryDao
import com.kom.skyfly.data.source.local.database.entity.SearchDestinationHistoryEntity
import com.kom.skyfly.data.source.local.database.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface SearchHistoryDataSource {
    fun getAllSearchHistory(): Flow<List<SearchHistoryEntity>>

    fun getAllSearchDestinationHistory(): Flow<List<SearchDestinationHistoryEntity>>

    suspend fun insertSearchHistory(searchHistory: SearchHistoryEntity): Long

    suspend fun insertSearchDestinationHistory(searchDestinationHistory: SearchDestinationHistoryEntity): Long

    suspend fun updateSearchHistory(searchHistory: SearchHistoryEntity): Int

    suspend fun updateSearchDestinationHistory(searchDestinationHistory: SearchDestinationHistoryEntity): Int

    suspend fun deleteSearchHistory(searchHistory: SearchHistoryEntity): Int

    suspend fun deleteSearchDestinationHistory(searchDestinationHistory: SearchDestinationHistoryEntity): Int

    suspend fun deleteAllSearchHistory()

    suspend fun deleteAllSearchDestinationHistory()
}

class SearchHistoryDataSourceImpl(private val dao: SearchHistoryDao) : SearchHistoryDataSource {
    override fun getAllSearchHistory(): Flow<List<SearchHistoryEntity>> = dao.getAllSearchHistory()

    override fun getAllSearchDestinationHistory(): Flow<List<SearchDestinationHistoryEntity>> = dao.getAllSearchDestinationHistory()

    override suspend fun insertSearchHistory(searchHistory: SearchHistoryEntity): Long = dao.insertSearchHistory(searchHistory)

    override suspend fun insertSearchDestinationHistory(searchDestinationHistory: SearchDestinationHistoryEntity): Long =
        dao.insertSearchDestinationHistory(
            searchDestinationHistory = searchDestinationHistory,
        )

    override suspend fun updateSearchHistory(searchHistory: SearchHistoryEntity): Int = dao.updateSearchHistory(searchHistory)

    override suspend fun updateSearchDestinationHistory(searchDestinationHistory: SearchDestinationHistoryEntity): Int =
        dao.updateSearchDestinationHistory(
            searchDestinationHistory = searchDestinationHistory,
        )

    override suspend fun deleteSearchHistory(searchHistory: SearchHistoryEntity): Int = dao.deleteSearchHistory(searchHistory)

    override suspend fun deleteSearchDestinationHistory(searchDestinationHistory: SearchDestinationHistoryEntity): Int =
        dao.deleteSearchDestinationHistory(
            searchDestinationHistory = searchDestinationHistory,
        )

    override suspend fun deleteAllSearchHistory() = dao.deleteAll()

    override suspend fun deleteAllSearchDestinationHistory() = dao.deleteAllRecentSearchDestination()
}
