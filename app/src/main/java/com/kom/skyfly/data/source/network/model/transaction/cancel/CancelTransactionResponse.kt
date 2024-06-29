package com.kom.skyfly.data.source.network.model.transaction.cancel

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CancelTransactionResponse(
    @SerializedName("data")
    val data: ItemsCancelTransactionResponse?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
)
