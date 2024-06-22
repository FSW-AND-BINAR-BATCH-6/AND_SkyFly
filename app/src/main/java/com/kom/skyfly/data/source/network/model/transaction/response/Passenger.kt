package com.kom.skyfly.data.source.network.model.transaction.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Passenger(
    @SerializedName("type")
    val type: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("dob")
    val dob: String?,
    @SerializedName("validityPeriod")
    val validityPeriod: String?,
    @SerializedName("familyName")
    val familyName: String?,
    @SerializedName("citizenship")
    val citizenship: String?,
    @SerializedName("passport")
    val passport: String?,
    @SerializedName("issuingCountry")
    val issuingCountry: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("quantity")
    val quantity: Int?,
    @SerializedName("seatId")
    val seatId: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
)
