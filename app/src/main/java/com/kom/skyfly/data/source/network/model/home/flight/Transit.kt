package com.kom.skyfly.data.source.network.model.home.flight

import com.google.gson.annotations.SerializedName
import com.kom.skyfly.data.source.network.model.common.Airport

data class Transit(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("arrivalDate")
    val arrivalDate: String,
    @SerializedName("arrivalTime")
    val arrivalTime: String,
    @SerializedName("departureDate")
    val departureDate: String,
    @SerializedName("departureTime")
    val departureTime: String,
    @SerializedName("transitAirport")
    val transitAirport: Airport,
)
