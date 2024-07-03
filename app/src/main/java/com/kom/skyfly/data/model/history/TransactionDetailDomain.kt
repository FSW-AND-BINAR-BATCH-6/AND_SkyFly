package com.kom.skyfly.data.model.history

data class TransactionDetailDomain(
    val citizenship: String,
    val dob: String,
    val familyName: String,
    val flight: FlightDomain,
    val id: String,
    val issuingCountry: String,
    val name: String,
    val passengerCategory: String,
    val passport: String,
    val seat: SeatDomain,
    val totalPrice: Int,
    val transactionId: String,
    val validityPeriod: String,
)
