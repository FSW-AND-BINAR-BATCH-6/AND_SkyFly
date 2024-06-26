package com.kom.skyfly.data.datasource.flightseat

import com.kom.skyfly.data.source.network.model.flightseat.FlightSeatResponse
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
class FlightSeatDataSourceImplTest {
    @MockK
    lateinit var service: SkyFlyApiService
    private lateinit var dataSource: FlightSeatDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = FlightSeatDataSourceImpl(service)
    }

    @Test
    fun getAllFlightSeat() {
        runTest {
            val mockResponse = mockk<FlightSeatResponse>(relaxed = true)
            coEvery { service.getAllFlightSeat(any()) } returns mockResponse
            val actualResult = dataSource.getAllFlightSeat("fefjehgeof")
            coVerify { service.getAllFlightSeat(any()) }
            assertEquals(mockResponse, actualResult)
        }
    }
}
