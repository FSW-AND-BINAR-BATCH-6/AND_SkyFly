package com.kom.skyfly.di

import com.kom.skyfly.data.datasource.auth.AuthDataSource
import com.kom.skyfly.data.datasource.auth.AuthDataSourceImpl
import com.kom.skyfly.data.repository.auth.AuthRepository
import com.kom.skyfly.data.repository.auth.AuthRepositoryImpl
import com.kom.skyfly.data.source.network.services.SkyFlyApiService
import com.kom.skyfly.presentation.login.LoginViewModel
import com.kom.skyfly.presentation.register.RegisterViewModel
import com.kom.skyfly.presentation.verifyotp.VerifyOtpViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
object AppModules {
    private val networkModule =
        module {
            single<SkyFlyApiService> { SkyFlyApiService.invoke() }
        }

    private val datasource =
        module {
            single<AuthDataSource> { AuthDataSourceImpl(get()) }
        }

    private val localModule =
        module {
        }

    private val repository =
        module {
            single<AuthRepository> { AuthRepositoryImpl(get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::LoginViewModel)
            viewModelOf(::RegisterViewModel)
            viewModelOf(::VerifyOtpViewModel)
        }

    val modules =
        listOf(
            networkModule,
            datasource,
            repository,
            localModule,
            viewModelModule,
        )
}
