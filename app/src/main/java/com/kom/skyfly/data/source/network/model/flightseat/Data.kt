package com.kom.skyfly.data.source.network.model.flightseat

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("flightId")
    val flightId: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("seatNumber")
    val seatNumber: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("type")
    val type: String?,
)
