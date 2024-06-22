package com.kom.skyfly.data.source.network.model.home.airport

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AirportResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("totalItems")
    val totalItems: Int,
    @SerializedName("pagination")
    val pagination: Pagination,
    @SerializedName("data")
    val data: List<AirportData>,
)
