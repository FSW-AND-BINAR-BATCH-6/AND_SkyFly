package com.kom.skyfly.data.datasource.userpreference

import com.kom.skyfly.data.source.local.pref.UserPreference
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserModelPrefItemsPaymentStatusModelSourceImplTest {
    @MockK
    lateinit var userPreference: UserPreference

    private lateinit var userPrefDataSource: UserPrefDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userPrefDataSource = UserPrefDataSourceImpl(userPreference)
    }

    @Test
    fun isOnBoardingShow() {
        every { userPreference.isOnBoardingShow() } returns true
        val result = userPrefDataSource.isOnBoardingShow()
        verify { userPreference.isOnBoardingShow() }
        assertEquals(true, result)
    }

    @Test
    fun setOnBoardingShow() {
        val isShow = true
        every { userPreference.setOnBoardingShow(isShow) } returns Unit
        userPrefDataSource.setOnBoardingShow(isShow)
        verify { userPreference.setOnBoardingShow(isShow) }
    }

    @Test
    fun saveUserToken() {
        every { userPreference.saveUserToken(any()) } returns Unit
        userPrefDataSource.saveUserToken("eyjieijefojgefo")
        verify { userPreference.saveUserToken(any()) }
    }

    @Test
    fun getUserToken() {
        val expectedToken = "eyjjfelhgbeneofe"
        every { userPreference.getUserToken() } returns expectedToken
        val result = userPrefDataSource.getUserToken()
        verify { userPreference.getUserToken() }
        assertEquals(expectedToken, result)
    }

    @Test
    fun clearAll() {
        every { userPreference.clearAll() } returns Unit
        userPrefDataSource.clearAll()
        verify { userPreference.clearAll() }
    }
}
