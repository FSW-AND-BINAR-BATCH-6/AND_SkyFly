package com.kom.skyfly.data.repository.notification

import com.kom.skyfly.data.datasource.notification.NotificationDataSource
import com.kom.skyfly.data.mapper.toNotifications
import com.kom.skyfly.data.model.notification.Notification
import com.kom.skyfly.utils.ResultWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
        return flow {
            emit(ResultWrapper.Loading())
            delay(1000)
            val result = notificationDataSource.getAllNotification().data.toNotifications()
            emit(ResultWrapper.Success(result))
        }
    }
}
