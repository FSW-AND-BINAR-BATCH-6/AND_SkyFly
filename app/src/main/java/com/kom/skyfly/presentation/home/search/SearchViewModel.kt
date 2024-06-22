package com.kom.skyfly.presentation.home.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.home.AirportRepository
import kotlinx.coroutines.Dispatchers

class SearchViewModel(
    private val repo: AirportRepository,
) : ViewModel() {
    fun getAllAirports(
        page: Int,
        limit: Int,
    ) = repo.getAllAirportData(page = page, limit = limit).asLiveData(Dispatchers.IO)
}
