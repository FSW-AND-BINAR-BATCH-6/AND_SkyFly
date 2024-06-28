package com.kom.skyfly.data.source.network.model.home.favourite_destination

import com.google.gson.annotations.SerializedName

data class SourceDestination(
    @SerializedName("departureDate")
    val departureDate: String,
    @SerializedName("departureCity")
    val departureCity: String,
)
