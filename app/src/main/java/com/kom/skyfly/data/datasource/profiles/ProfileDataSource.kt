package com.kom.skyfly.data.datasource.profiles

import com.kom.skyfly.data.source.network.model.userprofile.UserProfileResponse
import com.kom.skyfly.data.source.network.model.userprofile.updateprofile.UpdateProfileRequest
import com.kom.skyfly.data.source.network.model.userprofile.updateprofile.UpdateProfileResponse
import com.kom.skyfly.data.source.network.services.SkyFlyApiService

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface ProfileDataSource {
    suspend fun getUserProfile(): UserProfileResponse

    suspend fun updateUserProfile(
        name: String?,
        phoneNumber: String?,
        familyName: String?,
        password: String?,
        confirmPassword: String?,
    ): UpdateProfileResponse
}

class ProfileDataSourceImpl(private val service: SkyFlyApiService) : ProfileDataSource {
    override suspend fun getUserProfile(): UserProfileResponse {
        return service.getUserProfile()
    }

    override suspend fun updateUserProfile(
        name: String?,
        phoneNumber: String?,
        familyName: String?,
        password: String?,
        confirmPassword: String?,
    ): UpdateProfileResponse {
        val updateProfileRequest = UpdateProfileRequest(name, phoneNumber, familyName, password, confirmPassword)
        return service.updateUserProfile(updateProfileRequest)
    }
}
