package com.kom.skyfly.data.mapper.home

import com.kom.skyfly.data.model.home.destination_favourite.DestinationFavourite
import com.kom.skyfly.data.model.home.flight.FlightTicket
import com.kom.skyfly.data.model.home.flight_detail.FlightDetailTicket
import com.kom.skyfly.data.source.network.model.common.FlightData
import com.kom.skyfly.data.source.network.model.home.favourite_destination.FlightDetailResponse
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
        code = this?.plane?.code.orEmpty(),
        departureDate = this?.departureDate.orEmpty(),
        arrivalDate = this?.arrivalDate.orEmpty(),
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
        price = this?.seatClass?.first()?.seatPrice ?: 0,
        seatClass = this?.seatClass?.first()?.seatClassName.orEmpty(),
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

fun FlightDetailResponse?.toDestinationFavourite() =
    DestinationFavourite(
        id = this?.flightDetails?.flightId.orEmpty(),
        departureDate = this?.flightDetails?.sourceDestination?.departureDate.orEmpty(),
        departureCity = this?.flightDetails?.sourceDestination?.departureCity.orEmpty(),
        arrivalDate = this?.flightDetails?.arrivalDestination?.arrivalDate.orEmpty(),
        arrivalCity = this?.flightDetails?.arrivalDestination?.arrivalCity.orEmpty(),
        price = this?.flightDetails?.plane?.price ?: 0,
        img = this?.flightDetails?.arrivalDestination?.image.orEmpty(),
        airline = this?.flightDetails?.plane?.airline.orEmpty(),
    )

fun Collection<FlightData?>?.toFlightTickets() = this?.map { it.toFlightTicket() } ?: listOf()

fun Collection<FlightDetailData>?.toFlightDetailTickets() = this?.map { it.toFlightDetailTicket() } ?: listOf()

fun Collection<FlightDetailResponse>?.toDestinationFavourites() = this?.map { it.toDestinationFavourite() } ?: listOf()
