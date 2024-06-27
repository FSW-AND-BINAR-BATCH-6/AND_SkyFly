package com.kom.skyfly.data.source.network.services

import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.kom.skyfly.BuildConfig
import com.kom.skyfly.data.source.local.pref.UserPreference
import com.kom.skyfly.data.source.network.model.flightseat.FlightSeatResponse
import com.kom.skyfly.data.source.network.model.forgetpassword.ForgetPasswordRequest
import com.kom.skyfly.data.source.network.model.forgetpassword.ForgetPasswordResponse
import com.kom.skyfly.data.source.network.model.history.HistoryResponse
import com.kom.skyfly.data.source.network.model.home.airport.AirportResponse
import com.kom.skyfly.data.source.network.model.home.favourite_destination.FavouriteDestinationResponse
import com.kom.skyfly.data.source.network.model.home.flight.FlightResponse
import com.kom.skyfly.data.source.network.model.home.flight_detail.FlightDetailResponse
import com.kom.skyfly.data.source.network.model.login.LoginRequest
import com.kom.skyfly.data.source.network.model.login.LoginResponse
import com.kom.skyfly.data.source.network.model.notification.NotificationResponse
import com.kom.skyfly.data.source.network.model.register.RegisterRequest
import com.kom.skyfly.data.source.network.model.register.RegisterResponse
import com.kom.skyfly.data.source.network.model.resendotp.ResendOtpResponse
import com.kom.skyfly.data.source.network.model.resendotp.ResendOtpSmsRequest
import com.kom.skyfly.data.source.network.model.transaction.detail.TransactionDetailResponse
import com.kom.skyfly.data.source.network.model.transaction.request.TransactionRequest
import com.kom.skyfly.data.source.network.model.transaction.response.TransactionResponse
import com.kom.skyfly.data.source.network.model.userprofile.UserProfileResponse
import com.kom.skyfly.data.source.network.model.userprofile.updateprofile.UpdateProfileRequest
import com.kom.skyfly.data.source.network.model.userprofile.updateprofile.UpdateProfileResponse
import com.kom.skyfly.data.source.network.model.verifyaccount.VerifyAccountRequest
import com.kom.skyfly.data.source.network.model.verifyaccount.VerifyAccountResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface SkyFlyApiService {
    @POST("api/v1/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    ): LoginResponse

    @POST("api/v1/auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest,
    ): RegisterResponse

    @PUT("api/v1/auth/verified")
    suspend fun verifyAccount(
        @Query("token") token: String,
        @Body verifyAccountRequest: VerifyAccountRequest,
    ): VerifyAccountResponse

    @POST("api/v1/auth/forgetPassword")
    suspend fun forgetPassword(
        @Body forgetPasswordRequest: ForgetPasswordRequest,
    ): ForgetPasswordResponse

    @POST("api/v1/auth/verified/resend-otp")
    suspend fun resendOtp(
        @Query("token") token: String,
    ): ResendOtpResponse

    @GET("api/v1/flights/")
    suspend fun getAllFlights(
        @Query("search") search: String? = null,
        @Query("page") page: Int?,
        @Query("limit") limit: Int? = 10000,
        @Query("departureAirport") departureAirport: String?,
        @Query("arrivalAirport") arrivalAirport: String?,
        @Query("departureDate") departureDate: String?,
        @Query("returnDate") returnDate: String?,
        @Query("arrivalDate") arrivalDate: String? = null,
        @Query("seatClass") seatClass: String?,
        @Query("adult") adult: Int? = 0,
        @Query("children") children: Int? = 0,
        @Query("baby") baby: Int? = 0,
        @Query("sort") sort: String? = null,
    ): FlightResponse

    @GET("api/v1/flights/{id}")
    suspend fun getDetailFlightById(
        @Path("id") id: String,
        @Query("seatClass") seatClass: String?,
    ): FlightDetailResponse

    @GET("api/v1/airports/")
    suspend fun getAllAirports(
        @Query("city") city: String?,
        @Query("showall") showAll: Boolean = true,
    ): AirportResponse

    @GET("api/v1/flightSeats/flight/{id}")
    suspend fun getAllFlightSeat(
        @Path("id") id: String,
        @Query("limit") limit: Int? = 5000,
    ): FlightSeatResponse

    @GET("api/v1/flights/favorite-destination")
    suspend fun getDestinationFavourite(): FavouriteDestinationResponse

    @GET("api/v1/auth/me")
    suspend fun getUserProfile(): UserProfileResponse

    @PATCH("api/v1/auth/me")
    suspend fun updateUserProfile(
        @Body updateProfileRequest: UpdateProfileRequest,
    ): UpdateProfileResponse

    @POST("api/v1/transactions/payment")
    suspend fun createTransaction(
        @Query("flightId") flightId: String,
        @Query("adult") adult: Int,
        @Query("child") child: Int,
        @Query("baby") baby: Int,
        @Body transactionRequest: TransactionRequest,
    ): TransactionResponse

    @GET("api/v1/notifications")
    suspend fun getAllNotification(
        @Query("limit") limit: Int? = 5000,
    ): NotificationResponse

    @GET("api/v1/transactions/{id}")
    suspend fun getTransactionById(
        @Path("id") id: String,
    ): TransactionDetailResponse

    @POST("api/v1/auth/verified/resendSMS-otp")
    suspend fun resendOtpSms(
        @Query("token") token: String,
        @Body resendOtpRequest: ResendOtpSmsRequest,
    ): ResendOtpResponse

    @GET("api/v1/transactions")
    suspend fun getAllTransactionHistory(
        @Query("limit") limit: Int?,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?,
        @Query("flightCode") flightCode: String?,
    ): HistoryResponse

    companion object {
        @JvmStatic
        operator fun invoke(
            userPreference: UserPreference,
            chuckerInterceptor: ChuckerInterceptor,
        ): SkyFlyApiService {
            val logging =
                HttpLoggingInterceptor { message -> Log.d("Http-Logging", "log: $message") }
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient =
                OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .addInterceptor { chain ->
                        val original: Request = chain.request()
                        val token = userPreference.getUserToken()
                        // val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImUxOTM5OGQ4LTI3YTUtNDRkMi1iMzYxLTVjNDI2NjU0YjBiOCIsIm5hbWUiOiJrb21hbmd5dWRhIiwiZW1haWwiOiJ5dWRhc2FwdXRyYTA4MkBnbWFpbC5jb20iLCJwaG9uZU51bWJlciI6IjYyODc0Njc0NjQ3ODciLCJpYXQiOjE3MTgwMzQzMzksImV4cCI6MTcxODEyMDczOX0.r_vTbQwhr3NMZdGzfGyveF6rE-E1LOCC0BGv45dhNAw"
                        val requestBuilder: Request.Builder =
                            original.newBuilder()
                                .addHeader("accept", "application/json")
                                .addHeader("Authorization", "Bearer $token")
                        val request: Request = requestBuilder.build()
                        chain.proceed(request)
                    }
                    .addInterceptor(logging)
                    .addInterceptor(chuckerInterceptor)
                    .build()

            val retrofit =
                Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            return retrofit.create(SkyFlyApiService::class.java)
        }
    }
}
