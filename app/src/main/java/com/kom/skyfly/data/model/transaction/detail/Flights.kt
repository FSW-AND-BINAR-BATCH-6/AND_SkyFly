package com.kom.skyfly.data.model.transaction.detail

data class Flights(
    val airline: Airlines,
    val arrival: Arrivals,
    val code: String,
    val departure: Departures,
    val departureAirport: DepartureAirports,
    val destinationAirport: DestinationAirports,
    val flightDuration: String,
    val flightPrice: Int,
    val id: String,
)
