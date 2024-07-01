package com.kom.skyfly.data.model.tickets

import androidx.annotation.Keep

@Keep
data class TransactionDetailModel(
    val citizenship: String?,
    val dob: String?,
    val familyName: String?,
    val flightId: String?,
    val id: String?,
    val issuingCountry: String?,
    val name: String?,
    val passport: String?,
    val price: Int?,
    val seatId: String?,
    val transactionId: String?,
    val type: String?,
    val seat: SeatModel?,
    val validityPeriod: String?,
)
