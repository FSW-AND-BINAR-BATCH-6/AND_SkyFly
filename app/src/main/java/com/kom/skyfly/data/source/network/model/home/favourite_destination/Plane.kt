package com.kom.skyfly.data.source.network.model.home.favourite_destination

import com.google.gson.annotations.SerializedName

data class Plane(
    @SerializedName("airline")
    val airline: String,
    @SerializedName("price")
    val price: Int,
)
