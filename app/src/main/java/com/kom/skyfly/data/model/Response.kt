package com.kom.skyfly.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Response<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("totalItems")
    val totalItems: Int?,
)
