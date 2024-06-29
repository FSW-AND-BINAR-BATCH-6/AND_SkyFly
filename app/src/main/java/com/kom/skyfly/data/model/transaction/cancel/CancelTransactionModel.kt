package com.kom.skyfly.data.model.transaction.cancel

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CancelTransactionModel(
    @SerializedName("data")
    val data: ItemsCancelTransactionModel?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
)
