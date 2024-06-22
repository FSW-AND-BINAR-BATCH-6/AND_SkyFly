package com.kom.skyfly.data.source.network.model.notification

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ItemNotificationResponse(
    @SerializedName("date")
    val date: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("notificationsContent")
    val notificationsContent: String?,
    @SerializedName("notificationsTitle")
    val notificationsTitle: String?,
    @SerializedName("type")
    val type: String?,
)
