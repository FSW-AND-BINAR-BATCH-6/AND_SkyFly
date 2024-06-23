package com.kom.skyfly.presentation.home.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.home.airport.AirportRepository
import kotlinx.coroutines.Dispatchers

class SearchViewModel(
    private val repo: AirportRepository,
) : ViewModel() {
    fun getAllAirports() = repo.getAllAirportData().asLiveData(Dispatchers.IO)
}
