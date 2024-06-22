package com.kom.skyfly.data.datasource.notification

import com.kom.skyfly.data.source.network.model.notification.NotificationResponse
import com.kom.skyfly.data.source.network.services.SkyFlyApiService

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface NotificationDataSource {
    suspend fun getAllNotification(): NotificationResponse
}

class NotificationDataSourceImpl(private val service: SkyFlyApiService) : NotificationDataSource {
    override suspend fun getAllNotification(): NotificationResponse {
        return service.getAllNotification()
    }
}
