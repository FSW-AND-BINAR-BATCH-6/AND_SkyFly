package com.kom.skyfly.data.source.network.model.home.airport

import com.google.gson.annotations.SerializedName

data class AirportData(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("Country")
    val country: String,
    @SerializedName("City")
    val city: String,
)
