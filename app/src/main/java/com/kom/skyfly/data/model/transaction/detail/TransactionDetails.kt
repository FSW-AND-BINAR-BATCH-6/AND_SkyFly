package com.kom.skyfly.data.model.transaction.detail

data class TransactionDetails(
    val citizenship: String,
    val dob: String,
    val familyName: String,
    val flight: Flights,
    val id: String,
    val issuingCountry: String,
    val name: String,
    val passengerCategory: String,
    val passport: String,
    val seat: Seats,
    val totalPrice: Int,
    val transactionId: String,
    val validityPeriod: String,
)
