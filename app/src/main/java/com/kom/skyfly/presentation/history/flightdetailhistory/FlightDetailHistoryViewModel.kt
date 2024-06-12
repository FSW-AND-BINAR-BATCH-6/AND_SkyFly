package com.kom.skyfly.presentation.history.flightdetailhistory

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.kom.skyfly.data.model.history.Data
import com.kom.skyfly.data.repository.history.HistoryRepository

class FlightDetailHistoryViewModel(
    private val extras: Bundle?,
    private val historyRepository: HistoryRepository,
) : ViewModel() {
    val history = extras?.getParcelable<Data>(FlightDetailHistoryActivity.EXTRAS_HISTORY)

//    fun getDetailHistoryById(id: String) = historyRepository.getDetailHistoryById(id.toInt()).asLiveData(Dispatchers.IO)
}
