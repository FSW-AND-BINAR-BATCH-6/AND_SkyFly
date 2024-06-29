package com.kom.skyfly.data.source.network.model.history

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
    @SerializedName("flight")
    val flight: Flight?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("issuingCountry")
    val issuingCountry: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("passengerCategory")
    val passengerCategory: String?,
    @SerializedName("passport")
    val passport: String?,
    @SerializedName("seat")
    val seat: Seat?,
    @SerializedName("totalPrice")
    val totalPrice: Int?,
    @SerializedName("transactionId")
    val transactionId: String?,
    @SerializedName("validityPeriod")
    val validityPeriod: String?,
)
