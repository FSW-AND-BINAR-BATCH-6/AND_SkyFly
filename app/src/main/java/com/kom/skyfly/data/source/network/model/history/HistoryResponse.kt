package com.kom.skyfly.data.source.network.model.history

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class HistoryResponse(
    @SerializedName("data")
    val data: List<ItemsHistoryResponse?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("totalItems")
    val totalItems: Int?,
)
