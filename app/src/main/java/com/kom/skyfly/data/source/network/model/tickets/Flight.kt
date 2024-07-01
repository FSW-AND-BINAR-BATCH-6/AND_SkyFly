package com.kom.skyfly.data.source.network.model.tickets

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Flight(
    @SerializedName("arrivalDate")
    val arrivalDate: String?,
    @SerializedName("capacity")
    val capacity: Int?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("departureAirportId")
    val departureAirportId: String?,
    @SerializedName("departureDate")
    val departureDate: String?,
    @SerializedName("destinationAirportId")
    val destinationAirportId: String?,
    @SerializedName("discount")
    val discount: String?,
    @SerializedName("facilities")
    val facilities: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("planeId")
    val planeId: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("transitAirportId")
    val transitAirportId: String?,
    @SerializedName("transitArrivalDate")
    val transitArrivalDate: String?,
    @SerializedName("transitDepartureDate")
    val transitDepartureDate: String?,
)
