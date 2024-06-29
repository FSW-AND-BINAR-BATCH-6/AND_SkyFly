package com.kom.skyfly.data.source.network.model.paymentstatus

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VaNumber(
    @SerializedName("bank")
    val bank: String?,
    @SerializedName("va_number")
    val vaNumber: String?,
)
