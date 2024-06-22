package com.kom.skyfly.data.source.network.model.transaction.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Orderer(
    @SerializedName("email")
    val email: String?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("phone")
    val phone: String?,
)
