package com.kom.skyfly.presentation.account

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kom.skyfly.data.model.auth.Auth
import com.kom.skyfly.data.model.profiles.Profiles
import com.kom.skyfly.data.repository.auth.AuthRepository
import com.kom.skyfly.data.repository.profiles.ProfileRepository
import com.kom.skyfly.data.repository.userpref.UserPrefRepository
import com.kom.skyfly.data.source.network.model.forgetpassword.ForgetPasswordResponse
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
class AccountViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var authRepository: AuthRepository

    @MockK
    lateinit var userPrefRepository: UserPrefRepository

    @MockK
    lateinit var profileRepository: ProfileRepository

    private lateinit var viewModel: AccountViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(
                AccountViewModel(userPrefRepository, profileRepository, authRepository),
            )
    }

    @Test
    fun doLogout() {
        val token = "exampleToken"
        every { userPrefRepository.saveUserToken(token) } returns Unit
        viewModel.doLogout(token)
        verify { userPrefRepository.saveUserToken(token) }
    }

    @Test
    fun getProfile() {
        val mockkResponse = mockk<Profiles>(relaxed = true)
        every { profileRepository.getProfile() } returns
            flow {
                emit(ResultWrapper.Success(mockkResponse))
            }
        val result = viewModel.getProfile().getOrAwaitValue()
        assertEquals(mockkResponse, result.payload)
        verify { profileRepository.getProfile() }
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

    @Test
    fun isUserLoggedIn() {
        val mockkResponse = mockk<Auth>()
        every { authRepository.isUserLoggedIn() } returns
            flow {
                emit(ResultWrapper.Success(mockkResponse))
            }
        viewModel.isUserLoggedIn()
        verify { authRepository.isUserLoggedIn() }
    }
}
