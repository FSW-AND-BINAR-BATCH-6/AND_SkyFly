package com.kom.skyfly.data.model.history

data class TransactionDomain(
    val booking: BookingDomain,
    val id: String,
    val orderId: String,
    val status: String,
    val tax: Int,
    val totalPrice: Int,
    val transactionDetail: List<TransactionDetailDomain>,
    val userId: String,
)
