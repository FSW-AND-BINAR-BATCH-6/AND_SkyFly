package com.kom.skyfly.data.model.transaction.detail

data class FlightSeats(
    val flightId: String,
    val flightSeatId: String,
    val seatNumber: String,
    val status: String,
    val type: String,
    val price: Int,
)
