package com.kom.skyfly.data.source.network.model.home.flight

import com.google.gson.annotations.SerializedName

data class OriginalPrices(
    @SerializedName("FIRST")
    val firstClassPrice: Int,
    @SerializedName("ECONOMY")
    val economyClassPrice: Int,
    @SerializedName("BUSINESS")
    val businessClassPrice: Int,
)
