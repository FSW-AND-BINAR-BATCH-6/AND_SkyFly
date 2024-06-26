package com.kom.skyfly.data.repository.userpref

import com.kom.skyfly.data.datasource.userpreference.UserPrefDataSource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Written by Komang Yuda Saputra
 * Github : https://github.com/YudaSaputraa
 */
class UserPrefRepositoryImplTest {
    @MockK
    lateinit var dataSource: UserPrefDataSource
    private lateinit var userPrefRepository: UserPrefRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userPrefRepository = UserPrefRepositoryImpl(dataSource)
    }

    @Test
    fun isOnBoardingShow() {
        every { dataSource.isOnBoardingShow() } returns true

        val actualResult = userPrefRepository.isOnBoardingShow()

        assertTrue(actualResult)
        verify { dataSource.isOnBoardingShow() }
    }

    @Test
    fun setOnBoardingShow() {
        every { dataSource.setOnBoardingShow(any()) } returns Unit
        userPrefRepository.setOnBoardingShow(true)
        verify { dataSource.setOnBoardingShow(true) }
    }

    @Test
    fun saveUserToken() {
        every { dataSource.saveUserToken(any()) } returns Unit
        userPrefRepository.saveUserToken("eysjgjoejgbjfohg")
        verify { dataSource.saveUserToken(any()) }
    }

    @Test
    fun getUserToken() {
        val token = "eyjgepemfkgef"
        every { dataSource.getUserToken() } returns token
        val actualToken = userPrefRepository.getUserToken()
        assertEquals(token, actualToken)
        verify { dataSource.getUserToken() }
    }

    @Test
    fun clearAll() {
        every { dataSource.clearAll() } returns Unit
        userPrefRepository.clearAll()
        verify { dataSource.clearAll() }
    }
}
