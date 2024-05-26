package com.kom.skyfly.data.repository.auth

import com.kom.skyfly.data.datasource.auth.AuthDataSource
import com.kom.skyfly.data.source.network.model.register.RegisterResponse
import com.kom.skyfly.data.source.network.model.verifyaccount.VerifyAccountResponse
import com.kom.skyfly.utils.ResultWrapper
import com.kom.skyfly.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
    ): Flow<ResultWrapper<Boolean>>

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
}

class AuthRepositoryImpl(private val dataSource: AuthDataSource) : AuthRepository {
    override fun doLogin(
        email: String,
        password: String,
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.doLogin(email, password) }
    }

    override fun doRegister(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
    ): Flow<ResultWrapper<RegisterResponse>> {
        return flow {
            emit(ResultWrapper.Loading())
            delay(2000)
            val registerResponse = dataSource.doRegister(fullName, email, phoneNumber, password)
            emit(ResultWrapper.Success(registerResponse))
        }
    }

    override fun doVerifyAccount(
        token: String,
        otp: String,
    ): Flow<ResultWrapper<VerifyAccountResponse>> {
        return proceedFlow { dataSource.doVerifyAccount(token, otp) }
    }
}
