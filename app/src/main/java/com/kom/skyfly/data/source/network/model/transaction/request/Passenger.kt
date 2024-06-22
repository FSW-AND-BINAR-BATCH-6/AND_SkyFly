package com.kom.skyfly.data.source.network.model.transaction.request

import com.google.gson.annotations.SerializedName

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
data class Passenger(
    val type: String?,
    val title: String?,
    @SerializedName("fullName")
    val fullName: String?,
    val dob: String?,
    val validityPeriod: String?,
    val familyName: String?,
    val citizenship: String?,
    val passport: String?,
    val issuingCountry: String?,
    val price: Int?,
    val quantity: Int?,
    val seatId: String?,
)
