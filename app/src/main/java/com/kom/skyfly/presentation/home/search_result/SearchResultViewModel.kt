package com.kom.skyfly.presentation.home.search_result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.home.flight_ticket.FlightTicketRepository
import kotlinx.coroutines.Dispatchers

class SearchResultViewModel(
//    private val extras: Bundle?,
    private val flightRepository: FlightTicketRepository,
) : ViewModel() {
    fun getAllFlightTicket(
        search: String? = null,
        page: Int,
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String,
        returnDate: String? = null,
        seatClass: String?,
        limit: Int? = 20,
        arrivalDate: String? = null,
        adult: Int? = 1,
        children: Int? = 0,
        baby: Int? = 0,
        sort: String? = null,
    ) = flightRepository.getAllTicket(
        search = search,
        page = page,
        departureAirport = departureAirport,
        arrivalAirport = arrivalAirport,
        departureDate = departureDate,
        seatClass = seatClass,
        limit = limit,
        arrivalDate = arrivalDate,
        adult = adult,
        children = children,
        baby = baby,
        returnDate = returnDate,
        sort = sort,
    ).asLiveData(Dispatchers.IO)

    fun getReturnFlightTicket(
        search: String? = null,
        page: Int,
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String,
        returnDate: String? = null,
        seatClass: String?,
        limit: Int? = 20,
        arrivalDate: String? = null,
        adult: Int? = 1,
        children: Int? = 0,
        baby: Int? = 0,
        sort: String? = null,
    ) = flightRepository.getReturnTicket(
        search = search,
        page = page,
        departureAirport = departureAirport,
        arrivalAirport = arrivalAirport,
        departureDate = departureDate,
        seatClass = seatClass,
        limit = limit,
        arrivalDate = arrivalDate,
        adult = adult,
        children = children,
        baby = baby,
        returnDate = returnDate,
        sort = sort,
    ).asLiveData(Dispatchers.IO)

    // Set filterData
    private val _filterName = MutableLiveData<String?>(null)
    val filterName: LiveData<String?> get() = _filterName

    fun setFilterName(value: String?) {
        _filterName.value = value
    }
}
