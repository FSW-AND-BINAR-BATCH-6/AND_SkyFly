package com.kom.skyfly.data.source.network.model.resendotp

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResendOtpResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("_token")
    val token: String?,
)
