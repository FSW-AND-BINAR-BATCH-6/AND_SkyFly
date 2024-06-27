package com.kom.skyfly.data.source.network.model.home.favourite_destination

import com.google.gson.annotations.SerializedName

data class FavouriteDestinationData(
    @SerializedName("flightId")
    val flightId: String,
    @SerializedName("from")
    val sourceDestination: SourceDestination,
    @SerializedName("to")
    val arrivalDestination: ArrivalDestination,
    @SerializedName("plane")
    val plane: Plane,
)
