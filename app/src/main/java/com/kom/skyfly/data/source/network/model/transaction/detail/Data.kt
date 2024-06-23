package com.kom.skyfly.data.source.network.model.transaction.detail

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
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
    val transactionDetail: List<ItemTransactionDetail?>?,
    @SerializedName("userId")
    val userId: String?,
)
