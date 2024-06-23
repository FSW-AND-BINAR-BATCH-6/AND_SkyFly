package com.kom.skyfly.data.source.network.model.transaction.detail

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TransactionDetailResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
)
