package com.kom.skyfly.presentation.history.searchflighthistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kom.skyfly.data.model.searchhistory.SearchHistory
import com.kom.skyfly.data.repository.searchhistory.SearchHistoryRepository
import com.kom.skyfly.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class SearchFlightHistoryViewModel(private val searchHistoryRepository: SearchHistoryRepository) :
    ViewModel() {
    fun getAllSearchHistory() = searchHistoryRepository.getUserSearchHistory().asLiveData(Dispatchers.IO)

    fun deleteSearchHistory(item: SearchHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            searchHistoryRepository.deleteSearchHistory(item).collect()
        }
    }

    fun deleteAllSearchHistory() {
        viewModelScope.launch {
            searchHistoryRepository.deleteAllSearchHistory().collect()
        }
    }

    fun insertSearchHistory(
        userId: String,
        searchHistory: String,
    ): LiveData<ResultWrapper<Boolean>> {
        return searchHistoryRepository.createSearchHistory(userId, searchHistory)
            .asLiveData(Dispatchers.IO)
    }
}
