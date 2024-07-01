package com.kom.skyfly.data.model.tickets

import androidx.annotation.Keep

@Keep
data class SeatModel(
    val flightId: String?,
    val id: String?,
    val price: Int?,
    val seatNumber: String?,
    val status: String?,
    val type: String?,
)
