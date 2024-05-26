package com.kom.skyfly.data.source.network.model.verifyaccount

import com.google.gson.annotations.SerializedName

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
data class VerifyAccountRequest(
    @SerializedName("otp")
    val otp: String,
)
