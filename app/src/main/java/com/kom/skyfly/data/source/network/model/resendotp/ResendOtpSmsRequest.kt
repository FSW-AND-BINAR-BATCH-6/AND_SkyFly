package com.kom.skyfly.data.source.network.model.resendotp

import com.google.gson.annotations.SerializedName

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
data class ResendOtpSmsRequest(
    @SerializedName("phoneNumber") val phoneNumber: String,
)
