package com.kom.skyfly.data.model.transaction.detail

data class Datas(
    val booking: Bookings?,
    val id: String,
    val orderId: String,
    val status: String,
    val tax: Int,
    val totalPrice: Int,
    val transactionDetails: List<TransactionDetails?>?,
    val userId: String,
)
