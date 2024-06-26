package com.kom.skyfly.presentation.forgetpassword

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kom.skyfly.data.repository.auth.AuthRepository
import com.kom.skyfly.data.source.network.model.forgetpassword.ForgetPasswordResponse
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
class ForgetPasswordViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var authRepository: AuthRepository

    private lateinit var viewModel: ForgetPasswordViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(
                ForgetPasswordViewModel(authRepository),
            )
    }

    @Test
    fun forgetPassword() {
        val mockkResponse = mockk<ForgetPasswordResponse>(relaxed = true)
        every { authRepository.forgetPassword(any()) } returns
            flow {
                emit(ResultWrapper.Success(mockkResponse))
            }
        viewModel.forgetPassword("example@gmail.com")
        verify { authRepository.forgetPassword(any()) }
    }
}
