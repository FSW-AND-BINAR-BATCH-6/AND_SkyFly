package com.kom.skyfly.data.model.tickets

import androidx.annotation.Keep

@Keep
data class ItemsTicketsModel(
    val code: String?,
    val flight: FlightModel?,
    val flightId: String?,
    val id: String?,
    val seatId: String?,
    val ticketTransaction: TicketTransactionModel?,
    val ticketTransactionId: String?,
    val user: UserModel?,
    val userId: String?,
)
