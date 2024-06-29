package com.kom.skyfly.presentation.history.flightdetailhistory

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

    fun getPaymentStatus(id: String) =
        transactionRepository.getPaymentStatus(id)
            .asLiveData(Dispatchers.IO)

    fun cancelTransaction(id: String) =
        transactionRepository.cancelTransaction(id)
            .asLiveData(Dispatchers.IO)
}
