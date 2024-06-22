package com.kom.skyfly.presentation.checkout.passengerbiodata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.transaction.TransactionRepository
import com.kom.skyfly.data.source.network.model.transaction.request.TransactionRequest
import kotlinx.coroutines.Dispatchers

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class PassengerBiodataViewModel(private val transactionRepository: TransactionRepository) :
    ViewModel() {
    fun createTransaction(
        flightId: String,
        adult: Int,
        child: Int,
        baby: Int,
        transactionRequest: TransactionRequest,
    ) = transactionRepository.createTransaction(flightId, adult, child, baby, transactionRequest)
        .asLiveData(Dispatchers.IO)
}
