package com.kom.skyfly.data.source.network.model.userprofile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Auth(
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("isVerified")
    val isVerified: Boolean?,
)
