package com.kom.skyfly.data.source.network.model.home.flight

import com.google.gson.annotations.SerializedName
import com.kom.skyfly.data.source.network.model.common.Airport
import java.io.Serial

data class Transit(
    @SerializedName("arrivalDate")
    val arrivalDate: String,
    @SerializedName("arrivalTime")
    val arrivalTime: String,
    @SerializedName("departureDate")
    val departureDate: String,
    @SerializedName("departureTime")
    val departureTime: String,
    @SerializedName("transitAirport")
    val transitAirport: Airport
)
