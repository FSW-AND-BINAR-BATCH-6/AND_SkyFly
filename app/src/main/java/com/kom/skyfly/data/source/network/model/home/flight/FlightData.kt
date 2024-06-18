package com.kom.skyfly.data.source.network.model.home.flight

import com.google.gson.annotations.SerializedName
import com.kom.skyfly.data.source.network.model.common.Airport

data class FlightData(
    @SerializedName("id")
    val id: String,
    @SerializedName("planeId")
    val planeId: String,
    @SerializedName("plane")
    val plane: PlaneData,
    @SerializedName("departureDate")
    val departureDate: String,
    @SerializedName("departureTime")
    val departureTime: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("departureAirport")
    val departureAirport: Airport,
    @SerializedName("transit")
    val transit: Transit,
    @SerializedName("arrivalDate")
    val arrivalDate: String,
    @SerializedName("arrivalTime")
    val arrivalTime: String,
    @SerializedName("destinationAirport")
    val destinationAirport: Airport,
    @SerializedName("capacity")
    val capacity: Int,
    @SerializedName("discount")
    val discount: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("facilities")
    val facilities: String,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("seatClasses")
    val seatClasses: List<String>,
    @SerializedName("prices")
    val originalPrices: OriginalPrices,
)
