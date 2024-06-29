package com.kom.skyfly.data.source.network.model.paymentstatus

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PaymentStatusResponse(
    @SerializedName("data")
    val data: ItemsPaymentStatusResponse?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
)
