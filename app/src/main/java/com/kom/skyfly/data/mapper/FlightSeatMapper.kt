package com.kom.skyfly.data.mapper

import com.kom.skyfly.data.model.flightseat.FlightSeat
import com.kom.skyfly.data.source.network.model.flightseat.ItemFlightSeatResponse

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/

fun ItemFlightSeatResponse?.toFlightSeat() =
    FlightSeat(
        flightId = this?.flightId.orEmpty(),
        flightSeatId = this?.id.orEmpty(),
        seatNumber = this?.seatNumber.orEmpty(),
        status = this?.status.orEmpty(),
        type = this?.type.orEmpty(),
    )

fun Collection<ItemFlightSeatResponse>?.toFlightSeats() = this?.map { it.toFlightSeat() } ?: listOf()
