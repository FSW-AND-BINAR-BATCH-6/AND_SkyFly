package com.kom.skyfly.data.source.network.model.home.flight

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.kom.skyfly.data.source.network.model.common.FlightData
import com.kom.skyfly.data.source.network.model.home.airport.Pagination

@Keep
data class FlightResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("totalItems")
    val totalItems: Int,
    @SerializedName("pagination")
    val pagination: Pagination,
    @SerializedName("priceRanges")
    val priceRange: PriceRange,
    @SerializedName("data")
    val data: List<FlightData?>,
)
