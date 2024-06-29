package com.kom.skyfly.presentation.home.seatclass

import androidx.lifecycle.ViewModel
import com.kom.skyfly.data.repository.home.flight_ticket.FlightTicketRepository

class SeatClassViewModel(private val repo: FlightTicketRepository) : ViewModel() {
    fun getSeatClass() = repo.getSeatClassTicket()
}
