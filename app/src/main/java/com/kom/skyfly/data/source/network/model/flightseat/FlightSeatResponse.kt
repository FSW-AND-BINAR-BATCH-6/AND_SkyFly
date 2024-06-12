package com.kom.skyfly.data.source.network.model.flightseat

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FlightSeatResponse(
    @SerializedName("data")
    val data: List<Data>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("totalItems")
    val totalItems: Int?,
//    @SerializedName("pagination")
//    val pagination: List<Pagination>?,
)
