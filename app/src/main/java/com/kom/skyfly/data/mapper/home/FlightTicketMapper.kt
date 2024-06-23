package com.kom.skyfly.data.mapper.home

import com.kom.skyfly.data.model.home.flight.FlightTicket
import com.kom.skyfly.data.model.home.flight_detail.FlightDetailTicket
import com.kom.skyfly.data.source.network.model.common.FlightData
import com.kom.skyfly.data.source.network.model.home.flight_detail.FlightDetailData

fun FlightData?.toFlightTicket() =
    FlightTicket(
        id = this?.id.orEmpty(),
        departureCity = this?.departureAirport?.city.orEmpty(),
        arrivalCity = this?.destinationAirport?.city.orEmpty(),
        departureAirport = this?.departureAirport?.name.orEmpty(),
        arrivalAirport = this?.destinationAirport?.name.orEmpty(),
        departureCountryCode = this?.departureAirport?.code.orEmpty(),
        arrivalCountryCode = this?.destinationAirport?.code.orEmpty(),
        departureTime = this?.departureTime.orEmpty(),
        arrivalTime = this?.arrivalTime.orEmpty(),
        directNotes = this?.transit?.status ?: false,
        duration = this?.duration.orEmpty(),
        price = this?.seatClass?.seatPrice ?: 0,
        seatClass = this?.seatClass?.seatClassName.orEmpty(),
        airplaneName = this?.plane?.name.orEmpty(),
        airplaneImg = this?.plane?.image.orEmpty(),
        facilities = this?.facilities.orEmpty(),
        departureTerminal = this?.plane?.terminal.orEmpty(),
    )

fun FlightDetailData?.toFlightDetailTicket() =
    FlightDetailTicket(
        id = this?.id.orEmpty(),
        departureCity = this?.departureAirport?.city.orEmpty(),
        arrivalCity = this?.destinationAirport?.city.orEmpty(),
        departureAirport = this?.departureAirport?.name.orEmpty(),
        arrivalAirport = this?.destinationAirport?.name.orEmpty(),
        departureCountryCode = this?.departureAirport?.code.orEmpty(),
        arrivalCountryCode = this?.destinationAirport?.code.orEmpty(),
        departureTime = this?.departureTime.orEmpty(),
        arrivalTime = this?.arrivalTime.orEmpty(),
        transitNotes = this?.transit?.status ?: false,
        duration = this?.duration.orEmpty(),
        price = this?.price ?: 0,
        seatClass = "ECONOMY",
        airplaneName = this?.plane?.name.orEmpty(),
        airplaneImg = this?.plane?.image.orEmpty(),
        facilities =
            this?.facilities ?: (
                "Baggage 20 kg\n" +
                    "Cabin baggage 7 kg"
            ),
        departureTerminal = this?.plane?.terminal.orEmpty(),
        departureDate = this?.departureDate.orEmpty(),
        arrivalDate = this?.arrivalDate.orEmpty(),
        code = this?.code.orEmpty(),
    )

fun Collection<FlightData?>?.toFlightTickets() = this?.map { it.toFlightTicket() } ?: listOf()

fun Collection<FlightDetailData>?.toFlightDetailTickets() = this?.map { it.toFlightDetailTicket() } ?: listOf()
