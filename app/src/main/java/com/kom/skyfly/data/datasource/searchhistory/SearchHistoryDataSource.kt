package com.kom.skyfly.data.datasource.searchhistory

import com.kom.skyfly.data.source.local.database.dao.SearchHistoryDao
import com.kom.skyfly.data.source.local.database.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface SearchHistoryDataSource {
    fun getAllSearchHistory(): Flow<List<SearchHistoryEntity>>

    suspend fun insertSearchHistory(searchHistory: SearchHistoryEntity): Long

    suspend fun updateSearchHistory(searchHistory: SearchHistoryEntity): Int

    suspend fun deleteSearchHistory(searchHistory: SearchHistoryEntity): Int

    suspend fun deleteAllSearchHistory()
}

class SearchHistoryDataSourceImpl(private val dao: SearchHistoryDao) : SearchHistoryDataSource {
    override fun getAllSearchHistory(): Flow<List<SearchHistoryEntity>> = dao.getAllSearchHistory()

    override suspend fun insertSearchHistory(searchHistory: SearchHistoryEntity): Long = dao.insertSearchHistory(searchHistory)

    override suspend fun updateSearchHistory(searchHistory: SearchHistoryEntity): Int = dao.updateSearchHistory(searchHistory)

    override suspend fun deleteSearchHistory(searchHistory: SearchHistoryEntity): Int = dao.deleteSearchHistory(searchHistory)

    override suspend fun deleteAllSearchHistory() = dao.deleteAll()
}
