package com.kom.skyfly.data.datasource.auth

import com.kom.skyfly.data.source.network.model.forgetpassword.ForgetPasswordRequest
import com.kom.skyfly.data.source.network.model.forgetpassword.ForgetPasswordResponse
import com.kom.skyfly.data.source.network.model.login.LoginRequest
import com.kom.skyfly.data.source.network.model.register.RegisterRequest
import com.kom.skyfly.data.source.network.model.register.RegisterResponse
import com.kom.skyfly.data.source.network.model.verifyaccount.VerifyAccountRequest
import com.kom.skyfly.data.source.network.model.verifyaccount.VerifyAccountResponse
import com.kom.skyfly.data.source.network.services.SkyFlyApiService
import kotlin.Exception

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface AuthDataSource {
    @Throws(exceptionClasses = [Exception::class])
    suspend fun doLogin(
        email: String,
        password: String,
    ): Boolean

    @Throws(exceptionClasses = [Exception::class])
    suspend fun doRegister(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
    ): RegisterResponse

    @Throws(exceptionClasses = [Exception::class])
    suspend fun doVerifyAccount(
        token: String,
        otp: String,
    ): VerifyAccountResponse

    @Throws(exceptionClasses = [Exception::class])
    suspend fun forgetPassword(email: String): ForgetPasswordResponse
}

class AuthDataSourceImpl(private val service: SkyFlyApiService) : AuthDataSource {
    override suspend fun doLogin(
        email: String,
        password: String,
    ): Boolean {
        return try {
            val loginRequest = LoginRequest(email, password)
            val response = service.login(loginRequest)
            response.token != null
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun doRegister(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
    ): RegisterResponse {
        return try {
            val registerRequest = RegisterRequest(fullName, email, phoneNumber, password)
            service.register(registerRequest)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun doVerifyAccount(
        token: String,
        otp: String,
    ): VerifyAccountResponse {
        return service.verifyAccount(token, VerifyAccountRequest(otp))
    }

    override suspend fun forgetPassword(email: String): ForgetPasswordResponse {
        val forgetPasswordRequest = ForgetPasswordRequest(email)
        return service.forgetPassword(forgetPasswordRequest)
    }
}
