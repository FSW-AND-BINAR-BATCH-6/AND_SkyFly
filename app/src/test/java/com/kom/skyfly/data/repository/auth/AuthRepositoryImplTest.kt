package com.kom.skyfly.data.repository.auth

import app.cash.turbine.test
import com.kom.skyfly.data.datasource.auth.AuthDataSource
import com.kom.skyfly.data.source.network.model.forgetpassword.ForgetPasswordResponse
import com.kom.skyfly.data.source.network.model.login.LoginResponse
import com.kom.skyfly.data.source.network.model.register.RegisterResponse
import com.kom.skyfly.data.source.network.model.resendotp.ResendOtpResponse
import com.kom.skyfly.data.source.network.model.verifyaccount.VerifyAccountResponse
import com.kom.skyfly.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthRepositoryImplTest {
    @MockK
    lateinit var dataSource: AuthDataSource

    private lateinit var repository: AuthRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = AuthRepositoryImpl(dataSource)
    }

    @Test
    fun doLogin_success() {
        val mockResponse = mockk<LoginResponse>(relaxed = true)

        coEvery { dataSource.doLogin(any(), any()) } returns mockResponse

        runTest {
            repository.doLogin("komang@gmail.com", "password").map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockResponse, actualData.payload)
                coVerify { dataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun doLogin_loading() {
        val mockResponse = mockk<LoginResponse>(relaxed = true)

        coEvery { dataSource.doLogin(any(), any()) } returns mockResponse

        runTest {
            repository.doLogin("komang@gmail.com", "password").map {
                delay(100)
                it
            }.test {
                delay(111)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun doLogin_error() {
        val mockResponse = mockk<LoginResponse>(relaxed = true)

        coEvery { dataSource.doLogin(any(), any()) } throws IOException("loginError")

        runTest {
            repository.doLogin("komang@gmail.com", "password").map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun doRegister_success() {
        val mockResponse = mockk<LoginResponse>(relaxed = true)

        coEvery { dataSource.doLogin(any(), any()) } returns mockResponse

        runTest {
            repository.doLogin("komang@gmail.com", "password").map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockResponse, actualData.payload)
                coVerify { dataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun doRegister_loading() {
        val mockResponse = mockk<RegisterResponse>(relaxed = true)
        coEvery { dataSource.doRegister(any(), any(), any(), any()) } returns mockResponse
        runTest {
            repository.doRegister("Komangyuda", "komang@gmail.com", "6277487488", "password").map {
                delay(100)
                it
            }.test {
                delay(111)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.doRegister(any(), any(), any(), any()) }
            }
        }
    }

    @Test
    fun doRegister_error() {
        val mockResponse = mockk<RegisterResponse>(relaxed = true)
        coEvery {
            dataSource.doRegister(
                any(),
                any(),
                any(),
                any(),
            )
        } throws IOException("register error")
        runTest {
            repository.doRegister("Komangyuda", "komang@gmail.com", "6277487488", "password").map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.doRegister(any(), any(), any(), any()) }
            }
        }
    }

    @Test
    fun doVerifyAccount_success() {
        val mockResponse = mockk<VerifyAccountResponse>(relaxed = true)
        coEvery { dataSource.doVerifyAccount(any(), any()) } returns mockResponse

        runTest {
            repository.doVerifyAccount("eyjigoejogenmig", "675894").map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockResponse, actualData.payload)
                coVerify { dataSource.doVerifyAccount(any(), any()) }
            }
        }
    }

    @Test
    fun doVerifyAccount_error() {
        val mockResponse = mockk<VerifyAccountResponse>(relaxed = true)
        coEvery { dataSource.doVerifyAccount(any(), any()) } throws IOException("verify failed")

        runTest {
            repository.doVerifyAccount("eyjigoejogenmig", "675894").map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.doVerifyAccount(any(), any()) }
            }
        }
    }

    @Test
    fun doVerifyAccount_loading() {
        val mockResponse = mockk<VerifyAccountResponse>(relaxed = true)
        coEvery { dataSource.doVerifyAccount(any(), any()) } returns mockResponse

        runTest {
            repository.doVerifyAccount("eyjigoejogenmig", "675894").map {
                delay(100)
                it
            }.test {
                delay(111)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.doVerifyAccount(any(), any()) }
            }
        }
    }

    @Test
    fun forgetPassword_success() {
        val mockResponse = mockk<ForgetPasswordResponse>(relaxed = true)
        coEvery { dataSource.forgetPassword(any()) } returns mockResponse

        runTest {
            repository.forgetPassword("komang@gmail.com").map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockResponse, actualData.payload)
                coVerify { dataSource.forgetPassword(any()) }
            }
        }
    }

    @Test
    fun forgetPassword_error() {
        val mockResponse = mockk<ForgetPasswordResponse>(relaxed = true)
        coEvery { dataSource.forgetPassword(any()) } throws IOException("error")

        runTest {
            repository.forgetPassword("komang@gmail.com").map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.forgetPassword(any()) }
            }
        }
    }

    @Test
    fun forgetPassword_loading() {
        val mockResponse = mockk<ForgetPasswordResponse>(relaxed = true)
        coEvery { dataSource.forgetPassword(any()) } returns mockResponse

        runTest {
            repository.forgetPassword("komang@gmail.com").map {
                delay(100)
                it
            }.test {
                delay(111)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.forgetPassword(any()) }
            }
        }
    }

    @Test
    fun resendOtpRequest_success() {
        val mockResponse = mockk<ResendOtpResponse>(relaxed = true)
        coEvery { dataSource.resendOtpRequest(any()) } returns mockResponse

        runTest {
            repository.resendOtpRequest("eymkgejfeognnkfm").map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockResponse, actualData.payload)
                coVerify { dataSource.resendOtpRequest(any()) }
            }
        }
    }

    @Test
    fun resendOtpRequest_error() {
        val mockResponse = mockk<ResendOtpResponse>(relaxed = true)
        coEvery { dataSource.resendOtpRequest(any()) } throws IOException("error")

        runTest {
            repository.resendOtpRequest("eymkgejfeognnkfm").map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.resendOtpRequest(any()) }
            }
        }
    }

    @Test
    fun resendOtpSmsRequest_success() {
        val mockResponse = mockk<ResendOtpResponse>(relaxed = true)
        coEvery { dataSource.resendOtpSmsRequest(any(), any()) } returns mockResponse

        runTest {
            repository.resendOtpSmsRequest("eymkgejfeognnkfm", "6263774787").map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockResponse, actualData.payload)
                coVerify { dataSource.resendOtpSmsRequest(any(), any()) }
            }
        }
    }

    @Test
    fun resendOtpSmsRequest_error() {
        val mockResponse = mockk<ResendOtpResponse>(relaxed = true)
        coEvery { dataSource.resendOtpSmsRequest(any(), any()) } throws IOException("error")

        runTest {
            repository.resendOtpSmsRequest("eymkgejfeognnkfm", "6263774787").map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.resendOtpSmsRequest(any(), any()) }
            }
        }
    }

    @Test
    fun resendOtpSmsRequest_loading() {
        val mockResponse = mockk<ResendOtpResponse>(relaxed = true)
        coEvery { dataSource.resendOtpSmsRequest(any(), any()) } returns mockResponse

        runTest {
            repository.resendOtpSmsRequest("eymkgejfeognnkfm", "6263774787").map {
                delay(100)
                it
            }.test {
                delay(111)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.resendOtpSmsRequest(any(), any()) }
            }
        }
    }
}
