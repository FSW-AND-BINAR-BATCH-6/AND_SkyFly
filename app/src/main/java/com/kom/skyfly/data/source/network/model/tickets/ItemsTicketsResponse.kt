package com.kom.skyfly.data.source.network.model.tickets

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ItemsTicketsResponse(
    @SerializedName("code")
    val code: String?,
    @SerializedName("flight")
    val flight: Flight?,
    @SerializedName("flightId")
    val flightId: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("seatId")
    val seatId: String?,
    @SerializedName("ticketTransaction")
    val ticketTransaction: TicketTransaction?,
    @SerializedName("ticketTransactionId")
    val ticketTransactionId: String?,
    @SerializedName("user")
    val user: User?,
    @SerializedName("userId")
    val userId: String?,
)
