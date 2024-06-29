package com.kom.skyfly.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_destination_history")
data class SearchDestinationHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "search_history")
    var searchDestinationHistory: String,
)
