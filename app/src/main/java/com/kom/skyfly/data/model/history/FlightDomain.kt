package com.kom.skyfly.data.model.history

data class FlightDomain(
    val airline: AirlineDomain,
    val arrival: ArrivalDomain,
    val code: String,
    val departure: DepartureDomain,
    val departureAirport: DepartureAirportDomain,
    val destinationAirport: DestinationAirportDomain,
    val flightDuration: String,
    val flightPrice: Int,
    val id: String,
)
