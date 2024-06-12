package com.kom.skyfly.data.source.network.services

import android.util.Log
import com.kom.skyfly.BuildConfig
import com.kom.skyfly.data.source.local.pref.UserPreference
import com.kom.skyfly.data.source.network.model.flightseat.FlightSeatResponse
import com.kom.skyfly.data.source.network.model.forgetpassword.ForgetPasswordRequest
import com.kom.skyfly.data.source.network.model.forgetpassword.ForgetPasswordResponse
import com.kom.skyfly.data.source.network.model.home.airport.AirportResponse
import com.kom.skyfly.data.source.network.model.login.LoginRequest
import com.kom.skyfly.data.source.network.model.login.LoginResponse
import com.kom.skyfly.data.source.network.model.register.RegisterRequest
import com.kom.skyfly.data.source.network.model.register.RegisterResponse
import com.kom.skyfly.data.source.network.model.resendotp.ResendOtpResponse
import com.kom.skyfly.data.source.network.model.verifyaccount.VerifyAccountRequest
import com.kom.skyfly.data.source.network.model.verifyaccount.VerifyAccountResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
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
    )

    @GET("api/v1/airports/")
    suspend fun getAllAirports(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): AirportResponse

    @GET("api/v1/flightSeats/flight/{id}")
    suspend fun getAllFlightSeat(
        @Path("id") id: String,
        @Query("limit") limit: Int? = 10,
    ): FlightSeatResponse

    companion object {
        @JvmStatic
        operator fun invoke(userPreference: UserPreference): SkyFlyApiService {
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
                        val requestBuilder: Request.Builder =
                            original.newBuilder()
                                .addHeader("accept", "application/json")
                                .addHeader("Authorization", "Bearer $token")
                        val request: Request = requestBuilder.build()
                        chain.proceed(request)
                    }
                    .addInterceptor(logging)
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
