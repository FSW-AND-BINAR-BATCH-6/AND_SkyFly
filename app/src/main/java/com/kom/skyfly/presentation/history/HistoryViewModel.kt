package com.kom.skyfly.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.history.HistoryRepository
import kotlinx.coroutines.Dispatchers

class HistoryViewModel(
    private val historyRepository: HistoryRepository,
) : ViewModel() {
    fun getHistoryData(
        limit: Int?,
        startDate: String?,
        endDate: String?,
        flightCode: String?,
    ) = historyRepository.getHistoryData(limit, startDate, endDate, flightCode)
        .asLiveData(Dispatchers.IO)
}
