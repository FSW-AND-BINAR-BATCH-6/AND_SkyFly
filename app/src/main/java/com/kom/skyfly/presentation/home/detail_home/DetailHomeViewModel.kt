package com.kom.skyfly.presentation.home.detail_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.home.flight_ticket.FlightTicketRepository
import kotlinx.coroutines.Dispatchers

class DetailHomeViewModel(
    private val repo: FlightTicketRepository,
) : ViewModel() {
    fun getDetailTicketById(
        id: String?,
        seatClass: String?,
    ) = repo.getDetailTicket(id = id.orEmpty(), seatClass = seatClass).asLiveData(Dispatchers.IO)
}
