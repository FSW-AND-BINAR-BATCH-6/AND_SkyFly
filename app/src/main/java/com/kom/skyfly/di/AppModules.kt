package com.kom.skyfly.di

import android.content.SharedPreferences
import com.kom.skyfly.data.datasource.auth.AuthDataSource
import com.kom.skyfly.data.datasource.auth.AuthDataSourceImpl
import com.kom.skyfly.data.datasource.history.HistoryDataSource
import com.kom.skyfly.data.datasource.history.HistoryDataSourceImpl
import com.kom.skyfly.data.datasource.notification.NotificationDataSource
import com.kom.skyfly.data.datasource.notification.NotificationDataSourceImpl
import com.kom.skyfly.data.datasource.profiles.ProfileDataSource
import com.kom.skyfly.data.datasource.profiles.ProfileDataSourceImpl
import com.kom.skyfly.data.datasource.userpreference.UserPrefDataSource
import com.kom.skyfly.data.datasource.userpreference.UserPrefDataSourceImpl
import com.kom.skyfly.data.repository.auth.AuthRepository
import com.kom.skyfly.data.repository.auth.AuthRepositoryImpl
import com.kom.skyfly.data.repository.notification.NotificationRepository
import com.kom.skyfly.data.repository.notification.NotificationRepositoryImpl
import com.kom.skyfly.data.repository.profiles.ProfileRepository
import com.kom.skyfly.data.repository.profiles.ProfileRepositoryImpl
import com.kom.skyfly.data.repository.userpref.UserPrefRepository
import com.kom.skyfly.data.repository.userpref.UserPrefRepositoryImpl
import com.kom.skyfly.data.source.local.pref.UserPreference
import com.kom.skyfly.data.source.local.pref.UserPreferenceImpl
import com.kom.skyfly.data.source.network.services.SkyFlyApiService
import com.kom.skyfly.presentation.account.AccountViewModel
import com.kom.skyfly.presentation.forgetpassword.ForgetPasswordViewModel
import com.kom.skyfly.presentation.history.HistoryViewModel
import com.kom.skyfly.presentation.home.HomeViewModel
import com.kom.skyfly.presentation.login.LoginViewModel
import com.kom.skyfly.presentation.main.MainViewModel
import com.kom.skyfly.presentation.notification.NotificationViewModel
import com.kom.skyfly.presentation.onboarding.OnBoardingViewModel
import com.kom.skyfly.presentation.register.RegisterViewModel
import com.kom.skyfly.presentation.verifyotp.VerifyOtpViewModel
import com.kom.skyfly.utils.SharedPreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.scope.get
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
            single<UserPrefDataSource> { UserPrefDataSourceImpl(get()) }
            single<HistoryDataSource> { HistoryDataSourceImpl() }
            single<NotificationDataSource> { NotificationDataSourceImpl() }
            single<ProfileDataSource> { ProfileDataSourceImpl() }
        }

    private val localModule =
        module {
            single<UserPreference> { UserPreferenceImpl(get()) }
            single<SharedPreferences> {
                SharedPreferenceUtils.createPreference(
                    androidContext(),
                    UserPreferenceImpl.PREF_NAME,
                )
            }
        }

    private val repository =
        module {
            single<AuthRepository> { AuthRepositoryImpl(get()) }
            single<UserPrefRepository> { UserPrefRepositoryImpl(get()) }
            single<NotificationRepository> { NotificationRepositoryImpl(get()) }
            single<ProfileRepository> { ProfileRepositoryImpl(get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::LoginViewModel)
            viewModelOf(::RegisterViewModel)
            viewModelOf(::VerifyOtpViewModel)
            viewModelOf(::ForgetPasswordViewModel)
            viewModelOf(::HistoryViewModel)
            viewModelOf(::OnBoardingViewModel)
            viewModelOf(::HomeViewModel)
            viewModelOf(::MainViewModel)
            viewModelOf(::AccountViewModel)
            viewModelOf(::NotificationViewModel)
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
