package com.kom.skyfly.data.source.network.model.paymentstatus

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ItemsPaymentStatusResponse(
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("expiry_time")
    val expiryTime: String?,
    @SerializedName("gross_amount")
    val grossAmount: String?,
    @SerializedName("merchant_id")
    val merchantId: String?,
    @SerializedName("order_id")
    val orderId: String?,
    @SerializedName("payment_status")
    val paymentStatus: String?,
    @SerializedName("payment_type")
    val paymentType: String?,
    @SerializedName("signature_key")
    val signatureKey: String?,
    @SerializedName("transaction_id")
    val transactionId: String?,
    @SerializedName("transaction_status")
    val transactionStatus: String?,
    @SerializedName("transaction_time")
    val transactionTime: String?,
    @SerializedName("va_numbers")
    val vaNumbers: List<VaNumber?>?,
)
