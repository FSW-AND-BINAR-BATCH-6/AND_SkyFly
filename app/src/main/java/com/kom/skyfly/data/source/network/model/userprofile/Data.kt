package com.kom.skyfly.data.source.network.model.userprofile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("auth")
    val auth: Auth?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("role")
    val role: String?,
)
