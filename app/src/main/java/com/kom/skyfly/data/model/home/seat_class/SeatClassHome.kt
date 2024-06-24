package com.kom.skyfly.data.model.home.seat_class

import java.util.UUID

data class SeatClassHome(
    val id: String = UUID.randomUUID().toString(),
    val seatClassName: String,
)
