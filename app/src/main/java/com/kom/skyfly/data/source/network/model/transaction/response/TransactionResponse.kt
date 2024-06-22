package com.kom.skyfly.data.source.network.model.transaction.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TransactionResponse(
    @SerializedName("data")
    val data: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("redirect_url")
    val redirectUrl: String?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("_token")
    val token: String?,
)
