package com.kom.skyfly.data.model.home.intent

import android.os.Parcelable
import com.kom.skyfly.data.model.home.flight.FlightTicket
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResultIntent(
    val flightTicket: FlightTicket,
) : Parcelable
