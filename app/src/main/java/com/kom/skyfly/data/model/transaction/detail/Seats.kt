package com.kom.skyfly.data.model.transaction.detail

data class Seats(
    val flightId: String,
    val id: String,
    val seatNumber: String,
    val seatPrice: Int,
    val status: String,
    val type: String,
)
