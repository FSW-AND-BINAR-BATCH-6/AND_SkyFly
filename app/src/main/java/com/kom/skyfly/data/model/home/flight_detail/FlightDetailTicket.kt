package com.kom.skyfly.data.model.home.flight_detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlightDetailTicket(
    val id: String,
    val departureCity: String,
    val arrivalCity: String,
    val departureAirport: String,
    val arrivalAirport: String,
    val departureCountryCode: String,
    val arrivalCountryCode: String,
    val departureTime: String,
    val departureDate: String,
    val arrivalDate: String,
    val arrivalTime: String,
    val transitNotes: Boolean,
    val duration: String,
    val price: Int,
    val seatClass: String,
    val airplaneName: String,
    val airplaneImg: String,
    val code: String,
    val facilities: String,
    val departureTerminal: String,
) : Parcelable
