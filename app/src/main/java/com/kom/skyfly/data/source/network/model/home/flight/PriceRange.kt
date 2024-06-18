package com.kom.skyfly.data.source.network.model.home.flight

import com.google.gson.annotations.SerializedName

data class PriceRange(
    @SerializedName("FIRST")
    val firstClass: Price,
    @SerializedName("ECONOMY")
    val economyClass: Price,
    @SerializedName("BUSINESS")
    val businessClass: Price
)
