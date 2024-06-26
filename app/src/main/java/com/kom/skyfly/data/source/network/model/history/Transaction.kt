package com.kom.skyfly.data.source.network.model.history

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Transaction(
    @SerializedName("booking")
    val booking: Booking?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("orderId")
    val orderId: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tax")
    val tax: Int?,
    @SerializedName("totalPrice")
    val totalPrice: Int?,
    @SerializedName("Transaction_Detail")
    val transactionDetail: List<TransactionDetail?>?,
    @SerializedName("userId")
    val userId: String?,
)
