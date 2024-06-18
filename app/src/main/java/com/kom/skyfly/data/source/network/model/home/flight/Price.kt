package com.kom.skyfly.data.source.network.model.home.flight

import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("minPrice")
    val minPrice: String,
    @SerializedName("maxPrice")
    val maxPrice: String
)
