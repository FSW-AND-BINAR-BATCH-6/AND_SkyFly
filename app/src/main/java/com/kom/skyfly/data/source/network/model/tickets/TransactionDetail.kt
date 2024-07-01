package com.kom.skyfly.data.source.network.model.tickets

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TransactionDetail(
    @SerializedName("citizenship")
    val citizenship: String?,
    @SerializedName("dob")
    val dob: String?,
    @SerializedName("familyName")
    val familyName: String?,
    @SerializedName("flightId")
    val flightId: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("issuingCountry")
    val issuingCountry: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("passport")
    val passport: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("seatId")
    val seatId: String?,
    @SerializedName("transactionId")
    val transactionId: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("seat")
    val seat: Seat?,
    @SerializedName("validityPeriod")
    val validityPeriod: String?,
)
