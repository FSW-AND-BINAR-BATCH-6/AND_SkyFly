package com.kom.skyfly.data.model.tickets

import androidx.annotation.Keep

@Keep
data class TicketTransactionModel(
    val bookingCode: String?,
    val bookingDate: String?,
    val id: String?,
    val orderId: String?,
    val status: String?,
    val tax: Int?,
    val totalPrice: Int?,
    val transactionDetail: List<TransactionDetailModel?>?,
    val userId: String?,
)
