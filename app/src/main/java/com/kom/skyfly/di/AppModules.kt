package com.kom.skyfly.di

import android.content.SharedPreferences
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.kom.skyfly.core.BaseViewModel
import com.kom.skyfly.data.datasource.auth.AuthDataSource
import com.kom.skyfly.data.datasource.auth.AuthDataSourceImpl
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
import com.kom.skyfly.data.datasource.tickets.TicketsDataSource
import com.kom.skyfly.data.datasource.tickets.TicketsDataSourceImpl
import com.kom.skyfly.data.datasource.transaction.TransactionDataSource
import com.kom.skyfly.data.datasource.transaction.TransactionDataSourceImpl
import com.kom.skyfly.data.datasource.userpreference.UserPrefDataSource
import com.kom.skyfly.data.datasource.userpreference.UserPrefDataSourceImpl
import com.kom.skyfly.data.repository.auth.AuthRepository
import com.kom.skyfly.data.repository.auth.AuthRepositoryImpl
import com.kom.skyfly.data.repository.flightseat.FlightSeatRepository
import com.kom.skyfly.data.repository.flightseat.FlightSeatRepositoryImpl
import com.kom.skyfly.data.repository.history.HistoryRepository
import com.kom.skyfly.data.repository.history.HistoryRepositoryImpl
import com.kom.skyfly.data.repository.home.airport.AirportRepository
import com.kom.skyfly.data.repository.home.airport.AirportRepositoryImpl
import com.kom.skyfly.data.repository.home.destination_favourite.DestinationFavouriteRepository
import com.kom.skyfly.data.repository.home.destination_favourite.DestinationFavouriteRepositoryImpl
import com.kom.skyfly.data.repository.home.flight_ticket.FlightTicketRepository
import com.kom.skyfly.data.repository.home.flight_ticket.FlightTicketRepositoryImpl
import com.kom.skyfly.data.repository.notification.NotificationRepository
import com.kom.skyfly.data.repository.notification.NotificationRepositoryImpl
import com.kom.skyfly.data.repository.profiles.ProfileRepository
import com.kom.skyfly.data.repository.profiles.ProfileRepositoryImpl
import com.kom.skyfly.data.repository.searchhistory.SearchHistoryRepository
import com.kom.skyfly.data.repository.searchhistory.SearchHistoryRepositoryImpl
import com.kom.skyfly.data.repository.tickets.TicketsRepository
import com.kom.skyfly.data.repository.tickets.TicketsRepositoryImpl
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
import com.kom.skyfly.presentation.checkout.checkoutticket.CheckoutTicketViewModel
import com.kom.skyfly.presentation.checkout.chooseseat.ChooseSeatViewModel
import com.kom.skyfly.presentation.checkout.flightdetail.FlightDetailViewModel
import com.kom.skyfly.presentation.forgetpassword.ForgetPasswordViewModel
import com.kom.skyfly.presentation.history.HistoryViewModel
import com.kom.skyfly.presentation.history.flightdetailhistory.FlightDetailHistoryViewModel
import com.kom.skyfly.presentation.history.searchflighthistory.SearchFlightHistoryViewModel
import com.kom.skyfly.presentation.home.HomeViewModel
import com.kom.skyfly.presentation.home.detail_home.DetailHomeViewModel
import com.kom.skyfly.presentation.home.filter.FilterViewModel
import com.kom.skyfly.presentation.home.search.SearchViewModel
import com.kom.skyfly.presentation.home.search_result.SearchResultViewModel
import com.kom.skyfly.presentation.home.seatclass.SeatClassViewModel
import com.kom.skyfly.presentation.login.LoginViewModel
import com.kom.skyfly.presentation.main.MainViewModel
import com.kom.skyfly.presentation.notification.NotificationViewModel
import com.kom.skyfly.presentation.onboarding.OnBoardingViewModel
import com.kom.skyfly.presentation.register.RegisterViewModel
import com.kom.skyfly.presentation.ticket.TicketViewModel
import com.kom.skyfly.presentation.verifyotp.VerifyOtpViewModel
import com.kom.skyfly.utils.SharedPreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
object AppModules {
    private val networkModule =
        module {
            single<ChuckerInterceptor> { ChuckerInterceptor(androidContext()) }
            single<SkyFlyApiService> { SkyFlyApiService.invoke(get(), get()) }
        }

    private val datasource =
        module {
            single<AuthDataSource> { AuthDataSourceImpl(get()) }
            single<UserPrefDataSource> { UserPrefDataSourceImpl(get()) }
            single<HistoryDataSource> { HistoryDataSourceImpl(get()) }
            single<NotificationDataSource> { NotificationDataSourceImpl(get()) }
            single<ProfileDataSource> { ProfileDataSourceImpl(get()) }
            single<SearchHistoryDataSource> { SearchHistoryDataSourceImpl(get()) }
            single<HomeDataSource> { HomeDataSourceImpl(get()) }
            single<FlightSeatDataSource> { FlightSeatDataSourceImpl(get()) }
            single<TransactionDataSource> { TransactionDataSourceImpl(get()) }
            single<TicketsDataSource> { TicketsDataSourceImpl(get()) }
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
            single<AirportRepository> { AirportRepositoryImpl(get(), get()) }
            single<FlightSeatRepository> { FlightSeatRepositoryImpl(get()) }
            single<DestinationFavouriteRepository> { DestinationFavouriteRepositoryImpl(get()) }
            single<FlightTicketRepository> { FlightTicketRepositoryImpl(get()) }
            single<TransactionRepository> { TransactionRepositoryImpl(get()) }
            single<TicketsRepository> { TicketsRepositoryImpl(get()) }
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
            viewModelOf(::FilterViewModel)
            viewModelOf(::SearchResultViewModel)
            viewModelOf(::MainViewModel)
            viewModelOf(::AccountViewModel)
            viewModelOf(::NotificationViewModel)
            viewModelOf(::HistoryViewModel)
            viewModelOf(::SeatClassViewModel)
            viewModelOf(::DetailHomeViewModel)
            viewModelOf(::SearchFlightHistoryViewModel)
            viewModelOf(::BaseViewModel)
            viewModelOf(::SearchViewModel)
            viewModelOf(::ChooseSeatViewModel)
            viewModelOf(::FlightDetailHistoryViewModel)
            viewModelOf(::SharedViewModelEditProfile)
            viewModelOf(::BookersBiodataViewModel)
            viewModelOf(::CheckoutTicketViewModel)
            viewModelOf(::FlightDetailViewModel)
            viewModelOf(::TicketViewModel)
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
