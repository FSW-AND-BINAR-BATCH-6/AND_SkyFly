package com.kom.skyfly.data.source.network.model.verifyaccount

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VerifyAccountResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
)
