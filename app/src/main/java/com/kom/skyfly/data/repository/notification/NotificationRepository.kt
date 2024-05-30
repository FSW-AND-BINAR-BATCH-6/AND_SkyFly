package com.kom.skyfly.data.repository.notification

import com.kom.skyfly.data.datasource.notification.NotificationDataSource
import com.kom.skyfly.data.model.notification.Notification
import com.kom.skyfly.utils.ResultWrapper
import com.kom.skyfly.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface NotificationRepository {
    fun getAllNotification(): Flow<ResultWrapper<List<Notification>>>
}

class NotificationRepositoryImpl(private val notificationDataSource: NotificationDataSource) :
    NotificationRepository {
    override fun getAllNotification(): Flow<ResultWrapper<List<Notification>>> {
        return proceedFlow { notificationDataSource.getAllNotification() }
    }
}
