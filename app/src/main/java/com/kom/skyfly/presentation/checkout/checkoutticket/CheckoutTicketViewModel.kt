package com.kom.skyfly.presentation.checkout.checkoutticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.transaction.TransactionRepository
import kotlinx.coroutines.Dispatchers

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class CheckoutTicketViewModel(private val transactionRepository: TransactionRepository) :
    ViewModel() {
    fun getTransactionById(id: String) =
        transactionRepository
            .getTransactionById(id)
            .asLiveData(
                Dispatchers.IO,
            )
}
