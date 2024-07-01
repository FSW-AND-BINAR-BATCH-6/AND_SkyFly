package com.kom.skyfly.presentation.home.filter

import androidx.lifecycle.ViewModel
import com.kom.skyfly.data.repository.home.flight_ticket.FlightTicketRepository

class FilterViewModel(private val repo: FlightTicketRepository) : ViewModel() {
    fun getFilterName() = repo.getFilterData()
}
