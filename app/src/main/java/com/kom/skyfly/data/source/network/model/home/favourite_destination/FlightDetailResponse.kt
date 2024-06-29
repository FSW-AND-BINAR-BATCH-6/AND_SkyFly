package com.kom.skyfly.data.source.network.model.home.favourite_destination

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FlightDetailResponse(
    @SerializedName("flightDetails")
    val flightDetails: FavouriteDestinationData,
)
