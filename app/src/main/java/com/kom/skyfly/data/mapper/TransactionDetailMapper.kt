package com.kom.skyfly.data.mapper

import com.kom.skyfly.data.model.transaction.detail.Airlines
import com.kom.skyfly.data.model.transaction.detail.Arrivals
import com.kom.skyfly.data.model.transaction.detail.Bookings
import com.kom.skyfly.data.model.transaction.detail.Datas
import com.kom.skyfly.data.model.transaction.detail.DepartureAirports
import com.kom.skyfly.data.model.transaction.detail.Departures
import com.kom.skyfly.data.model.transaction.detail.DestinationAirports
import com.kom.skyfly.data.model.transaction.detail.Flights
import com.kom.skyfly.data.model.transaction.detail.Seats
import com.kom.skyfly.data.model.transaction.detail.TransactionDetailResponses
import com.kom.skyfly.data.model.transaction.detail.TransactionDetails
import com.kom.skyfly.data.source.network.model.transaction.detail.Airline
import com.kom.skyfly.data.source.network.model.transaction.detail.Arrival
import com.kom.skyfly.data.source.network.model.transaction.detail.Booking
import com.kom.skyfly.data.source.network.model.transaction.detail.Data
import com.kom.skyfly.data.source.network.model.transaction.detail.Departure
import com.kom.skyfly.data.source.network.model.transaction.detail.DepartureAirport
import com.kom.skyfly.data.source.network.model.transaction.detail.DestinationAirport
import com.kom.skyfly.data.source.network.model.transaction.detail.Flight
import com.kom.skyfly.data.source.network.model.transaction.detail.ItemTransactionDetail
import com.kom.skyfly.data.source.network.model.transaction.detail.Seat
import com.kom.skyfly.data.source.network.model.transaction.detail.TransactionDetailResponse

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/

fun ItemTransactionDetail?.toTransactionDetail() =
    this?.flight?.toFlight()?.let {
        this.seat?.toSeat()?.let { it1 ->
            TransactionDetails(
                citizenship = this.citizenship.orEmpty(),
                dob = this.dob.orEmpty(),
                familyName = this.familyName.orEmpty(),
                flight = it,
                id = this.id.orEmpty(),
                issuingCountry = this.issuingCountry.orEmpty(),
                name = this.name.orEmpty(),
                passengerCategory = this.passengerCategory.orEmpty(),
                passport = this.passport.orEmpty(),
                seat = it1,
                totalPrice = this.totalPrice ?: 0,
                transactionId = this.transactionId.orEmpty(),
                validityPeriod = this.validityPeriod.orEmpty(),
            )
        }
    }

fun Flight?.toFlight() =
    this?.airline?.toAirline()?.let {
        this.arrival?.toArrival()?.let { it1 ->
            this.departure?.toDeparture()?.let { it2 ->
                this.departureAirport?.toDepartureAirport()?.let { it3 ->
                    this.destinationAirport?.toDestinationAirport()?.let { it4 ->
                        Flights(
                            airline = it,
                            arrival = it1,
                            code = this.code.orEmpty(),
                            departure = it2,
                            departureAirport = it3,
                            destinationAirport = it4,
                            flightDuration = this.flightDuration.orEmpty(),
                            flightPrice = this.flightPrice ?: 0,
                            id = this.id.orEmpty(),
                        )
                    }
                }
            }
        }
    }

fun Airline?.toAirline() =
    Airlines(
        code = this?.code.orEmpty(),
        id = this?.id.orEmpty(),
        image = this?.image.orEmpty(),
        name = this?.name.orEmpty(),
        terminal = this?.terminal.orEmpty(),
    )

fun Arrival?.toArrival() =
    Arrivals(
        date = this?.date.orEmpty(),
        time = this?.time.orEmpty(),
    )

fun Booking?.toBooking() =
    Bookings(
        code = this?.code.orEmpty(),
        date = this?.date.orEmpty(),
        time = this?.time.orEmpty(),
    )

fun Departure?.toDeparture() =
    Departures(
        date = this?.date.orEmpty(),
        time = this?.time.orEmpty(),
    )

fun DepartureAirport?.toDepartureAirport() =
    DepartureAirports(
        city = this?.city.orEmpty(),
        code = this?.code.orEmpty(),
        continent = this?.continent.orEmpty(),
        country = this?.country.orEmpty(),
        id = this?.id.orEmpty(),
        image = this?.image.orEmpty(),
        name = this?.name.orEmpty(),
    )

fun DestinationAirport?.toDestinationAirport() =
    DestinationAirports(
        city = this?.city.orEmpty(),
        code = this?.code.orEmpty(),
        continent = this?.continent.orEmpty(),
        country = this?.country.orEmpty(),
        id = this?.id.orEmpty(),
        image = this?.image.orEmpty(),
        name = this?.name.orEmpty(),
    )

fun Seat?.toSeat() =
    Seats(
        flightId = this?.flightId.orEmpty(),
        id = this?.id.orEmpty(),
        seatNumber = this?.seatNumber.orEmpty(),
        seatPrice = this?.seatPrice ?: 0,
        status = this?.status.orEmpty(),
        type = this?.type.orEmpty(),
    )

fun Data?.toData() =
    Datas(
        booking = this?.booking?.toBooking(),
        id = this?.id.orEmpty(),
        orderId = this?.orderId.orEmpty(),
        status = this?.status.orEmpty(),
        tax = this?.tax ?: 0,
        totalPrice = this?.totalPrice ?: 0,
        transactionDetails = this?.transactionDetail?.toTransactionDetails(),
        userId = this?.userId.orEmpty(),
    )

fun TransactionDetailResponse?.toTransactionDetailResponse() =
    TransactionDetailResponses(
        data = this?.data?.toData(),
        message = this?.message.orEmpty(),
        status = this?.status ?: false,
    )

fun Collection<ItemTransactionDetail?>?.toTransactionDetails(): List<TransactionDetails?>? {
    return this?.map { it?.toTransactionDetail() }
}
