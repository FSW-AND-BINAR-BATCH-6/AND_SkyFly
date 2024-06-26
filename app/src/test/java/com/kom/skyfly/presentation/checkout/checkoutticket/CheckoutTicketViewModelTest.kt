package com.kom.skyfly.presentation.checkout.checkoutticket

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kom.skyfly.data.model.transaction.detail.TransactionDetailResponses
import com.kom.skyfly.data.repository.transaction.TransactionRepository
import com.kom.skyfly.tools.MainCoroutineRule
import com.kom.skyfly.tools.getOrAwaitValue
import com.kom.skyfly.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

/**
 * Written by Komang Yuda Saputra
 * Github : https://github.com/YudaSaputraa
 */
class CheckoutTicketViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var transactionRepository: TransactionRepository

    private lateinit var viewModel: CheckoutTicketViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(
                CheckoutTicketViewModel(transactionRepository),
            )
    }

    @Test
    fun getTransactionById() {
        val mockkResponse = mockk<TransactionDetailResponses>(relaxed = true)
        every { transactionRepository.getTransactionById(any()) } returns
            flow {
                emit(ResultWrapper.Success(mockkResponse))
            }
        val result = viewModel.getTransactionById("1").getOrAwaitValue()
        assertEquals(mockkResponse, result.payload)
    }
}
