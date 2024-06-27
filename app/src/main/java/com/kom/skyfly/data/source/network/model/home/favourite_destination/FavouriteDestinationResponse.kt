package com.kom.skyfly.data.source.network.model.home.favourite_destination

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FavouriteDestinationResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<FlightDetailResponse>,
)
