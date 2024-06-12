package com.kom.skyfly.data.model.detailhistory

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class HistoryDetail(
    val id: String = UUID.randomUUID().toString(),
    val paymentStatus: String,
    val bookingCode: String,
    val departureTime: String,
    val departureDate: String,
    // val departureStatus: String,
    // val departureAirport: String,
    // val departureTerminal: String,
    // val airlineName: String,
    val flightClass: String,
    // val flightNumber: String,
    // val passengerName: String,
    // val passengerId: String,
    val arrivalTime: String,
    val arrivalDate: String,
    // val destinationAirport: String,
    // val arrivalStatus: String,
    // val totalPassenger: String,
    // val price: Int,
    // val tax: Int,
    val total: Int,
    // sementara untuk dummy data
    val departureLocation: String,
    val destination: String,
    val flightDuration: String,
) : Parcelable
