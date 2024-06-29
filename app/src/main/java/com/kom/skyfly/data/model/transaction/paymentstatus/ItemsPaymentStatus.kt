package com.kom.skyfly.data.model.transaction.paymentstatus

import androidx.annotation.Keep

@Keep
data class ItemsPaymentStatus(
    val currency: String?,
    val expiryTime: String?,
    val grossAmount: String?,
    val merchantId: String?,
    val orderId: String?,
    val paymentStatus: String?,
    val paymentType: String?,
    val signatureKey: String?,
    val transactionId: String?,
    val transactionStatus: String?,
    val transactionTime: String?,
    val vaNumbers: List<VaNumberModel>?,
)
