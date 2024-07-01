package com.kom.skyfly.presentation.account.editprofile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kom.skyfly.data.repository.profiles.ProfileRepository
import com.kom.skyfly.data.source.network.model.userprofile.updateprofile.UpdateProfileResponse
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
class SharedViewModelEditProfileTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var profileRepository: ProfileRepository

    private lateinit var viewModel: SharedViewModelEditProfile

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(
                SharedViewModelEditProfile(profileRepository),
            )
    }

    @Test
    fun updateProfile() {
        val mockkResponse = mockk<UpdateProfileResponse>(relaxed = true)
        every { profileRepository.updateProfile(any(), any(), any(), any(), any()) } returns
            flow {
                emit(ResultWrapper.Success(mockkResponse))
            }
        viewModel.updateProfile("example name", "887387384", "examplepass", "examplepass", "vewfef")
        verify { profileRepository.updateProfile(any(), any(), any(), any(), any()) }
    }
}
