package com.kom.skyfly.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kom.skyfly.data.repository.auth.AuthRepository
import com.kom.skyfly.data.repository.userpref.UserPrefRepository
import com.kom.skyfly.data.source.network.model.login.LoginResponse
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
class LoginViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var authRepository: AuthRepository

    @MockK
    lateinit var userPrefRepository: UserPrefRepository
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(
                LoginViewModel(authRepository, userPrefRepository),
            )
    }

    @Test
    fun doLogin() {
        val mockkResponse = mockk<LoginResponse>(relaxed = true)
        every { authRepository.doLogin(any(), any()) } returns
            flow {
                emit(ResultWrapper.Success(mockkResponse))
            }
        viewModel.doLogin("example@gmail.com", "password")
        verify { authRepository.doLogin(any(), any()) }
    }

    @Test
    fun saveUserToken() {
        val token = "exampleToken"
        every { userPrefRepository.saveUserToken(token) } returns Unit
        viewModel.saveUserToken(token)
        verify { userPrefRepository.saveUserToken(token) }
    }
}
