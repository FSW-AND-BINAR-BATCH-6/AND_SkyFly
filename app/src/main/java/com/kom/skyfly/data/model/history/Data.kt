package com.kom.skyfly.data.model.history

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Data(
    val id: String = UUID.randomUUID().toString(),
    val status: String,
    val departureLocation: String,
    val departureDate: String,
    val departureTime: String,
    val flightDuration: String,
    val destination: String,
    val arrivalDate: String,
    val arrivalTime: String,
    val bookingCode: String,
    val flightClass: String,
    val total: String,
    val departureStatus: String,
    val departureAirport: String,
    val departureTerminal: String,
    val airlineName: String,
    val flightNumber: String,
    val passengerName: String,
    val passengerId: String,
    val destinationAirport: String,
    val arrivalStatus: String,
    val totalPassenger: String,
    val price: Int,
    val tax: Int,
) : Parcelable
