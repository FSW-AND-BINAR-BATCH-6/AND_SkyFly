package com.kom.skyfly.data.model.history

data class SeatDomain(
    val flightId: String,
    val id: String,
    val seatNumber: String,
    val seatPrice: Int,
    val status: String,
    val type: String,
)
