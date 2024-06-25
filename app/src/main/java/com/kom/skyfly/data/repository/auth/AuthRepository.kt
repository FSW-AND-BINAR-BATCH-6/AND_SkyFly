package com.kom.skyfly.data.repository.auth

import com.kom.skyfly.data.datasource.auth.AuthDataSource
import com.kom.skyfly.data.mapper.toUserIsLoggedIn
import com.kom.skyfly.data.model.auth.Auth
import com.kom.skyfly.data.source.network.model.forgetpassword.ForgetPasswordResponse
import com.kom.skyfly.data.source.network.model.login.LoginResponse
import com.kom.skyfly.data.source.network.model.register.RegisterResponse
import com.kom.skyfly.data.source.network.model.resendotp.ResendOtpResponse
import com.kom.skyfly.data.source.network.model.verifyaccount.VerifyAccountResponse
import com.kom.skyfly.utils.ResultWrapper
import com.kom.skyfly.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface AuthRepository {
    @Throws(exceptionClasses = [Exception::class])
    fun doLogin(
        email: String,
        password: String,
    ): Flow<ResultWrapper<LoginResponse>>

    @Throws(exceptionClasses = [Exception::class])
    fun doRegister(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
    ): Flow<ResultWrapper<RegisterResponse>>

    @Throws(exceptionClasses = [Exception::class])
    fun doVerifyAccount(
        token: String,
        otp: String,
    ): Flow<ResultWrapper<VerifyAccountResponse>>

    @Throws(exceptionClasses = [Exception::class])
    fun forgetPassword(email: String): Flow<ResultWrapper<ForgetPasswordResponse>>

    @Throws(exceptionClasses = [Exception::class])
    fun resendOtpRequest(token: String): Flow<ResultWrapper<ResendOtpResponse>>

    @Throws(exceptionClasses = [Exception::class])
    fun resendOtpSmsRequest(
        token: String,
        phoneNumber: String,
    ): Flow<ResultWrapper<ResendOtpResponse>>

    fun isUserLoggedIn(): Flow<ResultWrapper<Auth>>
}

class AuthRepositoryImpl(private val dataSource: AuthDataSource) : AuthRepository {
    override fun doLogin(
        email: String,
        password: String,
    ): Flow<ResultWrapper<LoginResponse>> {
        return proceedFlow { dataSource.doLogin(email, password) }
    }

    override fun doRegister(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
    ): Flow<ResultWrapper<RegisterResponse>> {
        return proceedFlow {
            dataSource.doRegister(fullName, email, phoneNumber, password)
        }
    }

    override fun doVerifyAccount(
        token: String,
        otp: String,
    ): Flow<ResultWrapper<VerifyAccountResponse>> {
        return proceedFlow { dataSource.doVerifyAccount(token, otp) }
    }

    override fun forgetPassword(email: String): Flow<ResultWrapper<ForgetPasswordResponse>> {
        return proceedFlow { dataSource.forgetPassword(email) }
    }

    override fun resendOtpRequest(token: String): Flow<ResultWrapper<ResendOtpResponse>> {
        return proceedFlow { dataSource.resendOtpRequest(token) }
    }

    override fun resendOtpSmsRequest(
        token: String,
        phoneNumber: String,
    ): Flow<ResultWrapper<ResendOtpResponse>> {
        return proceedFlow { dataSource.resendOtpSmsRequest(token, phoneNumber) }
    }

    override fun isUserLoggedIn(): Flow<ResultWrapper<Auth>> {
        return proceedFlow { dataSource.isUserLoggedIn().toUserIsLoggedIn() }
    }
}
