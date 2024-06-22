package com.kom.skyfly.data.source.network.model.userprofile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserProfileResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: Boolean?,
)
