package com.kom.skyfly.data.repository.notification

import app.cash.turbine.test
import com.kom.skyfly.data.datasource.notification.NotificationDataSource
import com.kom.skyfly.data.source.network.model.notification.ItemNotificationResponse
import com.kom.skyfly.data.source.network.model.notification.NotificationResponse
import com.kom.skyfly.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Written by Komang Yuda Saputra
 * Github : https://github.com/YudaSaputraa
 */
class NotificationRepositoryImplTest {
    @MockK
    lateinit var datasource: NotificationDataSource

    private lateinit var repository: NotificationRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = NotificationRepositoryImpl(datasource)
    }

    @Test
    fun getAllNotification_success() {
        val notification1 =
            ItemNotificationResponse(
                date = "2024-06-25T10:30:00Z",
                id = "1",
                notificationsContent = "This is notification content 1.",
                notificationsTitle = "Notification Title 1",
                type = "info",
            )

        val notification2 =
            ItemNotificationResponse(
                date = "2024-06-24T15:45:00Z",
                id = "2",
                notificationsContent = "This is notification content 2.",
                notificationsTitle = "Notification Title 2",
                type = "alert",
            )
        val mockListNotification = listOf(notification1, notification2)
        val mockResponse = mockk<NotificationResponse>()
        every { mockResponse.data } returns mockListNotification
        runTest {
            coEvery { datasource.getAllNotification() } returns mockResponse
            repository.getAllNotification().map {
                delay(100)
                it
            }.test {
                delay(1210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { datasource.getAllNotification() }
            }
        }
    }

    @Test
    fun getAllNotification_loading() {
        val notification1 =
            ItemNotificationResponse(
                date = "2024-06-25T10:30:00Z",
                id = "1",
                notificationsContent = "This is notification content 1.",
                notificationsTitle = "Notification Title 1",
                type = "info",
            )

        val notification2 =
            ItemNotificationResponse(
                date = "2024-06-24T15:45:00Z",
                id = "2",
                notificationsContent = "This is notification content 2.",
                notificationsTitle = "Notification Title 2",
                type = "alert",
            )
        val mockListNotification = listOf(notification1, notification2)
        val mockResponse = mockk<NotificationResponse>()
        every { mockResponse.data } returns mockListNotification
        runTest {
            coEvery { datasource.getAllNotification() } returns mockResponse
            repository.getAllNotification().map {
                delay(100)
                it
            }.test {
                delay(1111)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { datasource.getAllNotification() }
            }
        }
    }
}
