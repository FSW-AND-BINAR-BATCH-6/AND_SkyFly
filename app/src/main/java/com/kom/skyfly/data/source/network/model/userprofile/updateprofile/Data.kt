package com.kom.skyfly.data.source.network.model.userprofile.updateprofile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("familyName")
    val familyName: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("role")
    val role: String?,
)
