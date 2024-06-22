package com.kom.skyfly.data.source.network.model.transaction.request

import com.google.gson.annotations.SerializedName

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
data class Bookers(
    @SerializedName("fullName")
    val fullName: String,
    val familyName: String,
    val phoneNumber: String,
    val email: String,
)
