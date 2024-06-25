package com.kom.skyfly.data.source.network.model.home.flight

import com.google.gson.annotations.SerializedName
import com.kom.skyfly.data.source.network.model.common.Price

data class PriceRange(
    @SerializedName("FIRST")
    val firstClass: Price,
    @SerializedName("ECONOMY")
    val economyClass: Price,
    @SerializedName("BUSINESS")
    val businessClass: Price,
    val name: String? = "Business",
)
