package com.kom.skyfly.data.model.transaction.paymentstatus

import androidx.annotation.Keep

@Keep
data class PaymentStatusModel(
    val data: ItemsPaymentStatus?,
    val message: String?,
    val status: Boolean?,
)
