package com.kom.skyfly.data.mapper

import com.kom.skyfly.data.model.searchhistory.SearchHistory
import com.kom.skyfly.data.source.local.database.entity.SearchHistoryEntity

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
fun SearchHistory?.toSearchHistoryEntity() =
    SearchHistoryEntity(
        id = this?.id ?: 0,
        userId = this?.userId.orEmpty(),
        searchHistory = this?.searchHistory.orEmpty(),
    )

fun SearchHistoryEntity?.toSearchHistory() =
    SearchHistory(
        id = this?.id ?: 0,
        userId = this?.userId.orEmpty(),
        searchHistory = this?.searchHistory.orEmpty(),
    )

fun List<SearchHistoryEntity>?.toSearchHistoryList() = this?.map { it.toSearchHistory() }
