package com.kom.skyfly.data.mapper

import com.kom.skyfly.data.model.notification.Notification
import com.kom.skyfly.data.source.network.model.notification.ItemNotificationResponse

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
fun ItemNotificationResponse?.toNotification() =
    Notification(
        date = this?.date.orEmpty(),
        id = this?.id.orEmpty(),
        type = this?.type.orEmpty(),
        notificationsTitle = this?.notificationsTitle.orEmpty(),
        notificationsContent = this?.notificationsContent.orEmpty(),
    )

fun Collection<ItemNotificationResponse>?.toNotifications() = this?.map { it.toNotification() } ?: listOf()
