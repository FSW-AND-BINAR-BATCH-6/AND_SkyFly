package com.kom.skyfly.data.model.home.flight

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlightTicket(
    val id: String,
    val departureCity: String,
    val departureDate: String,
    val arrivalDate: String,
    val arrivalCity: String,
    val departureAirport: String,
    val arrivalAirport: String,
    val departureCountryCode: String,
    val arrivalCountryCode: String,
    val departureTime: String,
    val arrivalTime: String,
    val directNotes: Boolean,
    val duration: String,
    val price: Int,
    val code: String,
    val seatClass: String,
    val airplaneName: String,
    val airplaneImg: String,
    val facilities: String,
    val departureTerminal: String,
) : Parcelable
