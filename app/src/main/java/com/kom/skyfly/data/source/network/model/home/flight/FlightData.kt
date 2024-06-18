package com.kom.skyfly.data.source.network.model.home.flight

import com.google.gson.annotations.SerializedName

data class FlightData(
    @SerializedName("id")
    val id: String,
    @SerializedName("planeId")
    val planeId: String,
    @SerializedName("departureDate")
    val departureDate: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("departureAirport")
    val departureAirport : List<DepartureAirport>,
    @SerializedName("arrivalDate")
    val arrivalDate: String,

)
