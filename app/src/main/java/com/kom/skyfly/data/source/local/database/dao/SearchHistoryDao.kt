package com.kom.skyfly.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kom.skyfly.data.source.local.database.entity.SearchDestinationHistoryEntity
import com.kom.skyfly.data.source.local.database.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history ORDER BY id DESC")
    fun getAllSearchHistory(): Flow<List<SearchHistoryEntity>>

    @Query("SELECT * FROM search_destination_history ORDER BY id DESC")
    fun getAllSearchDestinationHistory(): Flow<List<SearchDestinationHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchHistory: SearchHistoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchDestinationHistory(searchDestinationHistory: SearchDestinationHistoryEntity): Long

    @Update
    suspend fun updateSearchHistory(searchHistory: SearchHistoryEntity): Int

    @Update
    suspend fun updateSearchDestinationHistory(searchDestinationHistory: SearchDestinationHistoryEntity): Int

    @Delete
    suspend fun deleteSearchHistory(searchHistory: SearchHistoryEntity): Int

    @Delete
    suspend fun deleteSearchDestinationHistory(searchDestinationHistory: SearchDestinationHistoryEntity): Int

    @Query("DELETE FROM search_history")
    suspend fun deleteAll()

    @Query("DELETE FROM search_destination_history")
    suspend fun deleteAllRecentSearchDestination()
}
