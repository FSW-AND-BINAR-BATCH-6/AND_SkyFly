package com.kom.skyfly.data.model.home.flight

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlightTicket(
    val id: String,
    val departureCountryCode: String,
    val arrivalCountryCode: String,
    val departureTime: String,
    val arrivalTime: String,
    val directNotes: String,
    val duration: String,
    val price: Int,
    val seatClass: String,
    val airplaneName: String,
//    val airplaneImg: String
) : Parcelable
