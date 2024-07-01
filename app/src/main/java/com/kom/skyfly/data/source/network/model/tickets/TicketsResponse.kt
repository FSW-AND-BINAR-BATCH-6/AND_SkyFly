package com.kom.skyfly.data.source.network.model.tickets

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TicketsResponse(
    @SerializedName("data")
    val data: List<ItemsTicketsResponse>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
)
