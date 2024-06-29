package com.kom.skyfly.data.repository.transaction

import app.cash.turbine.test
import com.kom.skyfly.data.datasource.transaction.TransactionDataSource
import com.kom.skyfly.data.source.network.model.transaction.request.Bookers
import com.kom.skyfly.data.source.network.model.transaction.request.Passenger
import com.kom.skyfly.data.source.network.model.transaction.request.TransactionRequest
import com.kom.skyfly.data.source.network.model.transaction.response.TransactionResponse
import com.kom.skyfly.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Written by Komang Yuda Saputra
 * Github : https://github.com/YudaSaputraa
 */
@ExperimentalCoroutinesApi
class TransactionRepositoryImplTest {
    @MockK
    lateinit var dataSource: TransactionDataSource
    private lateinit var repository: TransactionRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = TransactionRepositoryImpl(dataSource)
    }

    @Test
    fun createTransaction_success() =
        runTest {
            val flightId = "flight123"
            val adult = 2
            val child = 1
            val baby = 0
            val transactionRequest =
                TransactionRequest(
                    orderer =
                        Bookers(
                            "exampleFullName",
                            "ExampleFamilyName",
                            "62737478454",
                            "example@gmail.com",
                        ),
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

            val mockResponse =
                TransactionResponse(
                    data = null,
                    message = "Transaction created successfully",
                    redirectUrl = "/transaction/success",
                    status = true,
                    token = "token123",
                    transactionId = "transaction456",
                )

            coEvery {
                dataSource.createTransaction(
                    flightId,
                    adult,
                    child,
                    baby,
                    transactionRequest,
                )
            } returns mockResponse

            // Execute the repository method
            val flow =
                repository.createTransaction(flightId, adult, child, baby, transactionRequest).map {
                    delay(100)
                    it
                }.test {
                    delay(201)
                    val actualResult = expectMostRecentItem()
                    assertTrue(actualResult is ResultWrapper.Success)
                }

            coVerify {
                dataSource.createTransaction(
                    flightId,
                    adult,
                    child,
                    baby,
                    transactionRequest,
                )
            }
        }

    @Test
    fun createTransaction_loading() =
        runTest {
            val flightId = "flight123"
            val adult = 2
            val child = 1
            val baby = 0
            val transactionRequest =
                TransactionRequest(
                    orderer =
                        Bookers(
                            "exampleFullName",
                            "ExampleFamilyName",
                            "62737478454",
                            "example@gmail.com",
                        ),
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

            val mockResponse =
                TransactionResponse(
                    data = null,
                    message = "Transaction created successfully",
                    redirectUrl = "/transaction/success",
                    status = true,
                    token = "token123",
                    transactionId = "transaction456",
                )

            coEvery {
                dataSource.createTransaction(
                    flightId,
                    adult,
                    child,
                    baby,
                    transactionRequest,
                )
            } returns mockResponse

            val flow =
                repository.createTransaction(flightId, adult, child, baby, transactionRequest).map {
                    delay(100)
                    it
                }.test {
                    delay(111)
                    val actualResult = expectMostRecentItem()
                    assertTrue(actualResult is ResultWrapper.Loading)
                }

            coVerify {
                dataSource.createTransaction(
                    flightId,
                    adult,
                    child,
                    baby,
                    transactionRequest,
                )
            }
        }

    @Test
    fun createTransaction_error() =
        runTest {
            val flightId = "flight123"
            val adult = 2
            val child = 1
            val baby = 0
            val transactionRequest =
                TransactionRequest(
                    orderer =
                        Bookers(
                            "exampleFullName",
                            "ExampleFamilyName",
                            "62737478454",
                            "example@gmail.com",
                        ),
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

            val mockResponse =
                TransactionResponse(
                    data = null,
                    message = "Transaction created successfully",
                    redirectUrl = "/transaction/success",
                    status = true,
                    token = "token123",
                    transactionId = "transaction456",
                )

            coEvery {
                dataSource.createTransaction(
                    flightId,
                    adult,
                    child,
                    baby,
                    transactionRequest,
                )
            } throws IOException("error")

            val flow =
                repository.createTransaction(flightId, adult, child, baby, transactionRequest).map {
                    delay(100)
                    it
                }.test {
                    delay(201)
                    val actualResult = expectMostRecentItem()
                    assertTrue(actualResult is ResultWrapper.Error)
                }

            coVerify {
                dataSource.createTransaction(
                    flightId,
                    adult,
                    child,
                    baby,
                    transactionRequest,
                )
            }
        }

    @Test
    fun getTransactionById() {
    }
}
