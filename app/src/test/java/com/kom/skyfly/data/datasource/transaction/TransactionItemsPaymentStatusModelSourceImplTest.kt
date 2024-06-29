package com.kom.skyfly.data.datasource.transaction

import com.kom.skyfly.data.source.network.model.transaction.request.Bookers
import com.kom.skyfly.data.source.network.model.transaction.request.Passenger
import com.kom.skyfly.data.source.network.model.transaction.request.TransactionRequest
import com.kom.skyfly.data.source.network.model.transaction.response.TransactionResponse
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
class TransactionItemsPaymentStatusModelSourceImplTest {
    @MockK
    lateinit var service: SkyFlyApiService
    private lateinit var dataSource: TransactionDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = TransactionDataSourceImpl(service)
    }

    @Test
    fun createTransaction() {
        runTest {
            val flightId = "example_flight_id"
            val adultCount = 1
            val childCount = 0
            val babyCount = 1
            val mockTransactionRequest =
                TransactionRequest(
                    orderer = Bookers("exampleFullName", "ExampleFamilyName", "62737478454", "example@gmail.com"),
                    passengers =
                        listOf(
                            Passenger(
                                type = "Adult",
                                title = "Mr",
                                fullName = "John Doe",
                                dob = "1985-05-20",
                                validityPeriod = "2024-06-30",
                                familyName = "Doe",
                                citizenship = "US",
                                passport = "ABC123456",
                                issuingCountry = "USA",
                                price = 100,
                                quantity = 1,
                                seatId = "A1",
                            ),
                            Passenger(
                                type = "Child",
                                title = "Miss",
                                fullName = "Jane Doe",
                                dob = "2010-08-15",
                                validityPeriod = "2024-06-30",
                                familyName = "Doe",
                                citizenship = "US",
                                passport = "DEF789012",
                                issuingCountry = "USA",
                                price = 50,
                                quantity = 1,
                                seatId = "B2",
                            ),
                        ),
                )
            val mockResponse = mockk<TransactionResponse>(relaxed = true)
            coEvery { service.createTransaction(flightId, adultCount, childCount, babyCount, mockTransactionRequest) } returns mockResponse
            val actualResult = dataSource.createTransaction(flightId, adultCount, childCount, babyCount, mockTransactionRequest)
            coVerify {
                service.createTransaction(
                    flightId,
                    adultCount,
                    childCount,
                    babyCount,
                    mockTransactionRequest,
                )
            }
            assertEquals(mockResponse, actualResult)
        }
    }

    @Test
    fun getTransactionById() {
    }
}
