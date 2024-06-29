package com.kom.skyfly.data.model.transaction.paymentstatus

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VaNumberModel(
    @SerializedName("bank")
    val bank: String?,
    @SerializedName("va_number")
    val vaNumber: String?,
)
