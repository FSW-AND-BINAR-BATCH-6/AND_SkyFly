package com.kom.skyfly.data.source.network.model.transaction.detail

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Flight(
    @SerializedName("airline")
    val airline: Airline?,
    @SerializedName("arrival")
    val arrival: Arrival?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("departure")
    val departure: Departure?,
    @SerializedName("departureAirport")
    val departureAirport: DepartureAirport?,
    @SerializedName("destinationAirport")
    val destinationAirport: DestinationAirport?,
    @SerializedName("flightDuration")
    val flightDuration: String?,
    @SerializedName("flightPrice")
    val flightPrice: Int?,
    @SerializedName("id")
    val id: String?,
)
