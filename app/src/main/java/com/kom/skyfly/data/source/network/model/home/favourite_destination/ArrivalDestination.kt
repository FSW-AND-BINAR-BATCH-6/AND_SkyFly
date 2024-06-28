package com.kom.skyfly.data.source.network.model.home.favourite_destination

import com.google.gson.annotations.SerializedName

data class ArrivalDestination(
    @SerializedName("arrivalDate")
    val arrivalDate: String,
    @SerializedName("arrivalCity")
    val arrivalCity: String,
    @SerializedName("image")
    val image: String,
)
