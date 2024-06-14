package com.kom.skyfly.data.model.notification

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
@Parcelize
data class Notification(
    val id: String? = UUID.randomUUID().toString(),
    val type: String?,
    val notificationTitle: String,
    val notificationContent: String,
    val date: String,
) : Parcelable
