package com.kom.skyfly.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kom.skyfly.data.repository.searchhistory.SearchHistoryRepository
import com.kom.skyfly.data.repository.userpref.UserPrefRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class BaseViewModel(
    private val userPrefRepository: UserPrefRepository,
    private val searchHistoryRepository: SearchHistoryRepository,
) : ViewModel() {
    fun clearSession() {
        userPrefRepository.clearAll()
        viewModelScope.launch {
            searchHistoryRepository.deleteAllSearchHistory().collect()
        }
    }
}
