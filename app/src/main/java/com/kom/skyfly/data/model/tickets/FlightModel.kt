package com.kom.skyfly.data.model.tickets

import androidx.annotation.Keep

@Keep
data class FlightModel(
    val arrivalDate: String?,
    val capacity: Int?,
    val code: String?,
    val departureAirportId: String?,
    val departureDate: String?,
    val destinationAirportId: String?,
    val discount: String?,
    val facilities: String?,
    val id: String?,
    val planeId: String?,
    val price: Int?,
    val transitAirportId: String?,
    val transitArrivalDate: String?,
    val transitDepartureDate: String?,
)
