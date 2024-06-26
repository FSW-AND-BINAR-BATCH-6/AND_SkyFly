package com.kom.skyfly.presentation.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kom.skyfly.data.repository.auth.AuthRepository
import com.kom.skyfly.data.source.network.model.register.RegisterResponse
import com.kom.skyfly.tools.MainCoroutineRule
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
class RegisterViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var authRepository: AuthRepository

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(
                RegisterViewModel(authRepository),
            )
    }

    @Test
    fun doRegister() {
        val mockResponse = mockk<RegisterResponse>(relaxed = true)
        every { authRepository.doRegister(any(), any(), any(), any()) } returns
            flow {
                emit(ResultWrapper.Success(mockResponse))
            }
        viewModel.doRegister("exampleFullName", "example@gmail.com", "39839734", "examplePass")
        verify { authRepository.doRegister(any(), any(), any(), any()) }
    }
}
