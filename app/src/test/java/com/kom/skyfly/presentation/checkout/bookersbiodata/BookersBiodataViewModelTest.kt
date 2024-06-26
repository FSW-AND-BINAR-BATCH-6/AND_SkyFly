package com.kom.skyfly.presentation.checkout.bookersbiodata

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kom.skyfly.data.model.profiles.Profiles
import com.kom.skyfly.data.repository.profiles.ProfileRepository
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
class BookersBiodataViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var profileRepository: ProfileRepository

    private lateinit var viewModel: BookersBiodataViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(
                BookersBiodataViewModel(profileRepository),
            )
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
}
