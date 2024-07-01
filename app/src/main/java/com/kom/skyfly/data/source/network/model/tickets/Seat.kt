package com.kom.skyfly.data.source.network.model.tickets

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Seat(
    @SerializedName("flightId")
    val flightId: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("seatNumber")
    val seatNumber: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("type")
    val type: String?,
)
