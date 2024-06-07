package com.kom.skyfly.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "user_id")
    var userId: String? = null,
    @ColumnInfo(name = "search_history")
    var searchHistory: String,
)
