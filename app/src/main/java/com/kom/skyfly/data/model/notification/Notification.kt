package com.kom.skyfly.data.model.notification

import java.util.UUID

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
data class Notification(
    val id: String? = UUID.randomUUID().toString(),
    val type: String?,
    val notification: String,
    val date: String,
)
