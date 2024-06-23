package com.kom.skyfly.data.source.network.model.transaction.detail

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Departure(
    @SerializedName("date")
    val date: String?,
    @SerializedName("time")
    val time: String?,
)
