package com.kom.skyfly.data.model.notification

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
@Parcelize
data class Notification(
    val date: String?,
    val id: String?,
    val notificationsContent: String?,
    val notificationsTitle: String?,
    val type: String?,
) : Parcelable
