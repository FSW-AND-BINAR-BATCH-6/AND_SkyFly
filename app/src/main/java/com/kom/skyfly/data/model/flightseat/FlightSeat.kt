package com.kom.skyfly.data.model.flightseat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
@Parcelize
data class FlightSeat(
    var id: String? = UUID.randomUUID().toString(),
    val flightId: String?,
    val flightSeatId: String?,
    val seatNumber: String?,
    val status: String?,
    val type: String?,
) : Parcelable
