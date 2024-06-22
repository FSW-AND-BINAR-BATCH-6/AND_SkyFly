package com.kom.skyfly.data.source.network.model.transaction.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("orderer")
    val orderer: Orderer?,
    @SerializedName("passengers")
    val passengers: List<Passenger>?,
)
