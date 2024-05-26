package com.kom.skyfly.data.source.network.model.register

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RegisterResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("_token")
    val token: String?,
)
