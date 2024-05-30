package com.kom.skyfly.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.notification.NotificationRepository
import kotlinx.coroutines.Dispatchers

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class NotificationViewModel(
    private val notificationRepository: NotificationRepository,
) : ViewModel() {
    fun getAllNotification() = notificationRepository.getAllNotification().asLiveData(Dispatchers.IO)
}
