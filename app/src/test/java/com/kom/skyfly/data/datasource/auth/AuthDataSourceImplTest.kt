package com.kom.skyfly.data.datasource.auth

import com.kom.skyfly.data.source.network.model.forgetpassword.ForgetPasswordResponse
import com.kom.skyfly.data.source.network.model.login.LoginResponse
import com.kom.skyfly.data.source.network.model.register.RegisterResponse
import com.kom.skyfly.data.source.network.model.resendotp.ResendOtpResponse
import com.kom.skyfly.data.source.network.model.resendotp.ResendOtpSmsRequest
import com.kom.skyfly.data.source.network.model.userprofile.UserProfileResponse
import com.kom.skyfly.data.source.network.model.verifyaccount.VerifyAccountRequest
import com.kom.skyfly.data.source.network.model.verifyaccount.VerifyAccountResponse
import com.kom.skyfly.data.source.network.services.SkyFlyApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Written by Komang Yuda Saputra
 * Github : https://github.com/YudaSaputraa
 */
class AuthDataSourceImplTest {
    @MockK
    lateinit var service: SkyFlyApiService
    private lateinit var dataSource: AuthDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = AuthDataSourceImpl(service)
    }

    @Test
    fun doLogin() {
        runTest {
            val mockResponse = mockk<LoginResponse>(relaxed = true)
            coEvery { service.login(any()) } returns mockResponse
            val actualResult = dataSource.doLogin("example@gmail.com", "examplePassword")
            coVerify { service.login(any()) }
            assertEquals(mockResponse, actualResult)
        }
    }

    @Test
    fun doLogin_exception() {
        runTest {
            coEvery { service.login(any()) } throws Exception("Login failed")

            var thrownException: Throwable? = null
            try {
                kotlinx.coroutines.test.runTest {
                    dataSource.doLogin("example@gmail.com", "examplePassword")
                }
            } catch (e: Throwable) {
                thrownException = e
            }
            assertEquals("Login failed", thrownException?.message)
        }
    }

    @Test
    fun doRegister() {
        runTest {
            val mockResponse = mockk<RegisterResponse>(relaxed = true)
            coEvery { service.register(any()) } returns mockResponse
            val actualResult = dataSource.doRegister("exampleFullName", "example@gmail.com", "627848758844", "examplePassword")
            coVerify { service.register(any()) }
            assertEquals(mockResponse, actualResult)
        }
    }

    @Test
    fun doRegister_exception() {
        runTest {
            coEvery { service.register(any()) } throws Exception("register failed")

            var thrownException: Throwable? = null
            try {
                kotlinx.coroutines.test.runTest {
                    dataSource.doRegister("Komang", "example@gmail.com", "6267374874", "examplePassword")
                }
            } catch (e: Throwable) {
                thrownException = e
            }
            assertEquals("register failed", thrownException?.message)
        }
    }

    @Test
    fun doVerifyAccount() {
        runTest {
            val mockResponse = mockk<VerifyAccountResponse>(relaxed = true)
            coEvery {
                service.verifyAccount(
                    any(),
                    any<VerifyAccountRequest>(),
                )
            } returns mockResponse
            val actualResult = dataSource.doVerifyAccount("eyfejngekjrbgelrjnf", "56453")
            coVerify { service.verifyAccount(any(), any<VerifyAccountRequest>()) }
            assertEquals(mockResponse, actualResult)
        }
    }

    @Test
    fun forgetPassword() {
        runTest {
            val mockResponse = mockk<ForgetPasswordResponse>(relaxed = true)
            coEvery {
                service.forgetPassword(
                    any(),
                )
            } returns mockResponse
            val actualResult = dataSource.forgetPassword("example@gmail.com")
            coVerify { service.forgetPassword(any()) }
            assertEquals(mockResponse, actualResult)
        }
    }

    @Test
    fun resendOtpRequest() {
        runTest {
            val mockResponse = mockk<ResendOtpResponse>(relaxed = true)
            coEvery { service.resendOtp(any()) } returns mockResponse
            val actualResult = dataSource.resendOtpRequest("eyokmeengeurebgepfepfejf")
            coVerify { service.resendOtp(any()) }
            assertEquals(mockResponse, actualResult)
        }
    }

    @Test
    fun resendOtpSmsRequest() {
        runTest {
            val mockResponse = mockk<ResendOtpResponse>(relaxed = true)
            coEvery { service.resendOtpSms(any(), any<ResendOtpSmsRequest>()) } returns mockResponse
            val actualResult = dataSource.resendOtpSmsRequest("eyokmeengeurebgepfepfejf", "62674478474")
            coVerify { service.resendOtpSms(any(), any<ResendOtpSmsRequest>()) }
            assertEquals(mockResponse, actualResult)
        }
    }

    @Test
    fun resendOtpSmsRequest_exception() {
        runTest {
            coEvery { service.resendOtpSms(any(), any()) } throws Exception("resendOtpSms failed")

            var thrownException: Throwable? = null
            try {
                kotlinx.coroutines.test.runTest {
                    dataSource.resendOtpSmsRequest("eysjngknef", "6267374874")
                }
            } catch (e: Throwable) {
                thrownException = e
            }
            assertEquals("resendOtpSms failed", thrownException?.message)
        }
    }

    @Test
    fun isUserLoggedIn() {
        runTest {
            val mockResponse = mockk<UserProfileResponse>(relaxed = true)
            coEvery { service.getUserProfile() } returns mockResponse

            val actualResult = dataSource.isUserLoggedIn()

            coVerify { service.getUserProfile() }
            assertEquals(mockResponse, actualResult)
        }
    }
}
