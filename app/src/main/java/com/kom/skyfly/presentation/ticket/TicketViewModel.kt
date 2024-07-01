package com.kom.skyfly.presentation.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.tickets.TicketsRepository
import kotlinx.coroutines.Dispatchers

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class TicketViewModel(private val ticketsRepository: TicketsRepository) : ViewModel() {
    fun getTicketById(id: String) =
        ticketsRepository
            .getTicketsById(id)
            .asLiveData(Dispatchers.IO)
}
