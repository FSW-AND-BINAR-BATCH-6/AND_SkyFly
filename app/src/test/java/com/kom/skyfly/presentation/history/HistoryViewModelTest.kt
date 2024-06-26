package com.kom.skyfly.presentation.history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kom.skyfly.data.model.history.new.HistoryDomain
import com.kom.skyfly.data.repository.history.HistoryRepository
import com.kom.skyfly.tools.MainCoroutineRule
import com.kom.skyfly.tools.getOrAwaitValue
import com.kom.skyfly.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
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
class HistoryViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var historyRepository: HistoryRepository

    private lateinit var viewModel: HistoryViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(
                HistoryViewModel(
                    historyRepository,
                ),
            )
    }

    @Test
    fun getHistoryData() {
        val mockkResponse = mockk<HistoryDomain>(relaxed = true)
        every { historyRepository.getHistoryData(any(), any(), any(), any()) } returns
            flow {
                emit(ResultWrapper.Success(mockkResponse))
            }
        val result = viewModel.getHistoryData(null, null, null, null).getOrAwaitValue()
        assertEquals(mockkResponse, result.payload)
        verify { historyRepository.getHistoryData(any(), any(), any(), any()) }
    }
}
