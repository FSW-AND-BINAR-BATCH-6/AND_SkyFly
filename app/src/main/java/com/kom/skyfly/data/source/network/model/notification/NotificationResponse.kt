package com.kom.skyfly.data.source.network.model.notification

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NotificationResponse(
    @SerializedName("data")
    val data: List<ItemNotificationResponse>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("pagination")
    val pagination: Pagination?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("totalItems")
    val totalItems: Int?,
)
