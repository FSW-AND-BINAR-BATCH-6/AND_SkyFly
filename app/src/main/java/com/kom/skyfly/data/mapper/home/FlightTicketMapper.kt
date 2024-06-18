package com.kom.skyfly.data.mapper.home

import com.kom.skyfly.data.model.home.flight.FlightTicket
import com.kom.skyfly.data.source.network.model.home.flight.FlightData

fun FlightData?.toFlightTicket() =
    FlightTicket(
        id = this?.id.orEmpty(),
        departureCountryCode = this?.departureAirport?.code.orEmpty(),
        arrivalCountryCode = this?.destinationAirport?.code.orEmpty(),
        departureTime = this?.departureTime.orEmpty(),
        arrivalTime = this?.arrivalTime.orEmpty(),
        directNotes = "Direct",
        duration = this?.duration.orEmpty(),
        price = this?.price ?: 0,
        seatClass = "Economy",
        airplaneName = this?.plane?.name.orEmpty()

    )
fun Collection<FlightData>?.toFlightTickets() = this?.map { it.toFlightTicket() } ?: listOf()
