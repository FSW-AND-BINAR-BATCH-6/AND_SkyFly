package com.kom.skyfly.data.source.network.model.forgetpassword

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ForgetPasswordResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
)
