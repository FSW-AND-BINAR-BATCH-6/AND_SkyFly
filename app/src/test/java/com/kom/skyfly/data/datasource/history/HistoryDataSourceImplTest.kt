package com.kom.skyfly.data.datasource.history

import com.kom.skyfly.data.source.network.model.history.HistoryResponse
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
class HistoryDataSourceImplTest {
    @MockK
    lateinit var service: SkyFlyApiService
    private lateinit var dataSource: HistoryDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = HistoryDataSourceImpl(service)
    }

    @Test
    fun getHistoryData() {
        runTest {
            val mockResponse = mockk<HistoryResponse>(relaxed = true)
            coEvery {
                service.getAllTransactionHistory(
                    any(),
                    any(),
                    any(),
                    any(),
                )
            } returns mockResponse
            val actualResult = dataSource.getHistoryData(5000, null, null, null)
            coVerify { service.getAllTransactionHistory(any(), any(), any(), any()) }
            assertEquals(mockResponse, actualResult)
        }
    }
}
