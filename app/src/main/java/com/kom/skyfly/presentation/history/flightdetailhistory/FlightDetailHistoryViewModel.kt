package com.kom.skyfly.presentation.history.flightdetailhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.transaction.TransactionRepository
import kotlinx.coroutines.Dispatchers

class FlightDetailHistoryViewModel(
    private val transactionRepository: TransactionRepository,
) : ViewModel() {
    fun getHistoryById(id: String) =
        transactionRepository
            .getTransactionById(id)
            .asLiveData(Dispatchers.IO)
}

class SharedSearchViewModel : ViewModel() {
    private val _flightCode = MutableLiveData<String>()

    val flightCode: LiveData<String>
        get() = _flightCode

    fun setFlightCode(code: String) {
        _flightCode.value = code
    }
}
