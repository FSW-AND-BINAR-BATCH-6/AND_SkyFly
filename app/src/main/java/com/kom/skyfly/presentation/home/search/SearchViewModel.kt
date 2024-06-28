package com.kom.skyfly.presentation.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kom.skyfly.data.model.home.search_history.SearchDestinationHistory
import com.kom.skyfly.data.repository.home.airport.AirportRepository
import com.kom.skyfly.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repo: AirportRepository,
) : ViewModel() {
    fun getAllAirports(city: String? = null) = repo.getAllAirportData(city = city).asLiveData(Dispatchers.IO)

    fun getAllSearchHistory() = repo.getUserSearchDestinationHistory().asLiveData(Dispatchers.IO)

    fun deleteSearchHistory(item: SearchDestinationHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteSearchDestinationHistory(item).collect()
        }
    }

    fun deleteAllSearchHistory() {
        viewModelScope.launch {
            repo.deleteAllSearchDestinationHistory().collect()
        }
    }

    fun insertSearchHistory(searchDestinationHistory: String): LiveData<ResultWrapper<Boolean>> {
        return repo.createSearchDestinationHistory(searchDestinationHistory)
            .asLiveData(Dispatchers.IO)
    }
}
