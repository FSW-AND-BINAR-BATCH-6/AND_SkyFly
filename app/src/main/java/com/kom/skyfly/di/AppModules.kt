package com.kom.skyfly.di

import android.content.SharedPreferences
import android.os.Bundle
import com.kom.skyfly.core.BaseViewModel
import com.kom.skyfly.data.datasource.auth.AuthDataSource
import com.kom.skyfly.data.datasource.auth.AuthDataSourceImpl
import com.kom.skyfly.data.datasource.destinationfavorite.DestinationFavoriteDataSource
import com.kom.skyfly.data.datasource.destinationfavorite.DestinationFavoriteDataSourceImpl
import com.kom.skyfly.data.datasource.flightseat.FlightSeatDataSource
import com.kom.skyfly.data.datasource.flightseat.FlightSeatDataSourceImpl
import com.kom.skyfly.data.datasource.history.HistoryDataSource
import com.kom.skyfly.data.datasource.history.HistoryDataSourceImpl
import com.kom.skyfly.data.datasource.home.HomeDataSource
import com.kom.skyfly.data.datasource.home.HomeDataSourceImpl
import com.kom.skyfly.data.datasource.notification.NotificationDataSource
import com.kom.skyfly.data.datasource.notification.NotificationDataSourceImpl
import com.kom.skyfly.data.datasource.profiles.ProfileDataSource
import com.kom.skyfly.data.datasource.profiles.ProfileDataSourceImpl
import com.kom.skyfly.data.datasource.searchhistory.SearchHistoryDataSource
import com.kom.skyfly.data.datasource.searchhistory.SearchHistoryDataSourceImpl
import com.kom.skyfly.data.datasource.transaction.TransactionDataSource
import com.kom.skyfly.data.datasource.transaction.TransactionDataSourceImpl
import com.kom.skyfly.data.datasource.userpreference.UserPrefDataSource
import com.kom.skyfly.data.datasource.userpreference.UserPrefDataSourceImpl
import com.kom.skyfly.data.repository.auth.AuthRepository
import com.kom.skyfly.data.repository.auth.AuthRepositoryImpl
import com.kom.skyfly.data.repository.destinationfavorite.DestinationFavoriteRepository
import com.kom.skyfly.data.repository.destinationfavorite.DestinationFavoriteRepositoryImpl
import com.kom.skyfly.data.repository.flightseat.FlightSeatRepository
import com.kom.skyfly.data.repository.flightseat.FlightSeatRepositoryImpl
import com.kom.skyfly.data.repository.history.HistoryRepository
import com.kom.skyfly.data.repository.history.HistoryRepositoryImpl
import com.kom.skyfly.data.repository.home.airport.AirportRepository
import com.kom.skyfly.data.repository.home.airport.AirportRepositoryImpl
import com.kom.skyfly.data.repository.home.flight_ticket.FlightTicketRepository
import com.kom.skyfly.data.repository.home.flight_ticket.FlightTicketRepositoryImpl
import com.kom.skyfly.data.repository.notification.NotificationRepository
import com.kom.skyfly.data.repository.notification.NotificationRepositoryImpl
import com.kom.skyfly.data.repository.profiles.ProfileRepository
import com.kom.skyfly.data.repository.profiles.ProfileRepositoryImpl
import com.kom.skyfly.data.repository.searchhistory.SearchHistoryRepository
import com.kom.skyfly.data.repository.searchhistory.SearchHistoryRepositoryImpl
import com.kom.skyfly.data.repository.transaction.TransactionRepository
import com.kom.skyfly.data.repository.transaction.TransactionRepositoryImpl
import com.kom.skyfly.data.repository.userpref.UserPrefRepository
import com.kom.skyfly.data.repository.userpref.UserPrefRepositoryImpl
import com.kom.skyfly.data.source.local.database.AppDatabase
import com.kom.skyfly.data.source.local.database.dao.SearchHistoryDao
import com.kom.skyfly.data.source.local.pref.UserPreference
import com.kom.skyfly.data.source.local.pref.UserPreferenceImpl
import com.kom.skyfly.data.source.network.services.SkyFlyApiService
import com.kom.skyfly.presentation.account.AccountViewModel
import com.kom.skyfly.presentation.account.editprofile.SharedViewModelEditProfile
import com.kom.skyfly.presentation.checkout.bookersbiodata.BookersBiodataViewModel
import com.kom.skyfly.presentation.checkout.chooseseat.ChooseSeatViewModel
import com.kom.skyfly.presentation.checkout.passengerbiodata.PassengerBiodataViewModel
import com.kom.skyfly.presentation.forgetpassword.ForgetPasswordViewModel
import com.kom.skyfly.presentation.history.HistoryViewModel
import com.kom.skyfly.presentation.history.flightdetailhistory.FlightDetailHistoryViewModel
import com.kom.skyfly.presentation.history.searchflighthistory.SearchFlightHistoryViewModel
import com.kom.skyfly.presentation.home.HomeViewModel
import com.kom.skyfly.presentation.home.detail_home.DetailHomeViewModel
import com.kom.skyfly.presentation.home.search.SearchViewModel
import com.kom.skyfly.presentation.home.search_result.SearchResultViewModel
import com.kom.skyfly.presentation.login.LoginViewModel
import com.kom.skyfly.presentation.main.MainViewModel
import com.kom.skyfly.presentation.notification.NotificationViewModel
import com.kom.skyfly.presentation.onboarding.OnBoardingViewModel
import com.kom.skyfly.presentation.register.RegisterViewModel
import com.kom.skyfly.presentation.verifyotp.VerifyOtpViewModel
import com.kom.skyfly.utils.SharedPreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
object AppModules {
    private val networkModule =
        module {
            single<SkyFlyApiService> { SkyFlyApiService.invoke(get()) }
        }

    private val datasource =
        module {
            single<AuthDataSource> { AuthDataSourceImpl(get()) }
            single<UserPrefDataSource> { UserPrefDataSourceImpl(get()) }
            single<HistoryDataSource> { HistoryDataSourceImpl() }
            single<NotificationDataSource> { NotificationDataSourceImpl(get()) }
            single<ProfileDataSource> { ProfileDataSourceImpl(get()) }
            single<SearchHistoryDataSource> { SearchHistoryDataSourceImpl(get()) }
            single<HomeDataSource> { HomeDataSourceImpl(get()) }
            single<FlightSeatDataSource> { FlightSeatDataSourceImpl(get()) }
            single<DestinationFavoriteDataSource> { DestinationFavoriteDataSourceImpl() }
            single<TransactionDataSource> { TransactionDataSourceImpl(get()) }
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
            single<AppDatabase> { AppDatabase.createInstance(androidContext()) }
            single<SearchHistoryDao> { get<AppDatabase>().searchHistoryDao() }
        }

    private val repository =
        module {
            single<AuthRepository> { AuthRepositoryImpl(get()) }
            single<UserPrefRepository> { UserPrefRepositoryImpl(get()) }
            single<NotificationRepository> { NotificationRepositoryImpl(get()) }
            single<HistoryRepository> { HistoryRepositoryImpl(get()) }
            single<ProfileRepository> { ProfileRepositoryImpl(get()) }
            single<SearchHistoryRepository> { SearchHistoryRepositoryImpl(get()) }
            single<AirportRepository> { AirportRepositoryImpl(get()) }
            single<FlightSeatRepository> { FlightSeatRepositoryImpl(get()) }
            single<DestinationFavoriteRepository> { DestinationFavoriteRepositoryImpl(get()) }
            single<TransactionRepository> { TransactionRepositoryImpl(get()) }
            single<FlightTicketRepository> { FlightTicketRepositoryImpl(get()) }
            single<FlightTicketRepository> { FlightTicketRepositoryImpl(get()) }
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
            viewModelOf(::SearchResultViewModel)
            viewModelOf(::MainViewModel)
            viewModelOf(::AccountViewModel)
            viewModelOf(::NotificationViewModel)
            viewModelOf(::HistoryViewModel)
            viewModelOf(::DetailHomeViewModel)
            viewModelOf(::SearchFlightHistoryViewModel)
            viewModelOf(::BaseViewModel)
            viewModelOf(::SearchViewModel)
            viewModelOf(::ChooseSeatViewModel)
            viewModel { (extras: Bundle?) ->
                FlightDetailHistoryViewModel(
                    extras = extras,
                    historyRepository = get(),
                )
            }
            viewModelOf(::SharedViewModelEditProfile)
            viewModelOf(::BookersBiodataViewModel)
            viewModelOf(::PassengerBiodataViewModel)
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
