package com.kom.skyfly.data.source.network.model.userprofile.updateprofile

import com.google.gson.annotations.SerializedName

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
data class UpdateProfileRequest(
    @SerializedName("name") val name: String?,
    @SerializedName("phoneNumber") val phoneNumber: String?,
    @SerializedName("familyName") val familyName: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("confirmPassword") val confirmPassword: String?,
)
