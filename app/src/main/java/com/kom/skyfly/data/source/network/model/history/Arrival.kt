package com.kom.skyfly.data.source.network.model.history

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Arrival(
    @SerializedName("date")
    val date: String?,
    @SerializedName("time")
    val time: String?,
)
