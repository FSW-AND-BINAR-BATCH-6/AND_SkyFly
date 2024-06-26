package com.kom.skyfly.data.datasource.notification

import com.kom.skyfly.data.source.network.model.notification.NotificationResponse
import com.kom.skyfly.data.source.network.services.SkyFlyApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Written by Komang Yuda Saputra
 * Github : https://github.com/YudaSaputraa
 */
class NotificationDataSourceImplTest {
    @MockK
    lateinit var service: SkyFlyApiService
    private lateinit var dataSource: NotificationDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = NotificationDataSourceImpl(service)
    }

    @Test
    fun getAllNotification() {
        runTest {
            val mockResponse = mockk<NotificationResponse>(relaxed = true)
            coEvery { service.getAllNotification(any()) } returns mockResponse
            val actualResult = dataSource.getAllNotification()
            coVerify { service.getAllNotification(any()) }
            assertEquals(mockResponse, actualResult)
        }
    }
}
