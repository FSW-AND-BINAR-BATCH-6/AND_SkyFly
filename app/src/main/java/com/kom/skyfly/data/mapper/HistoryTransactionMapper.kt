package com.kom.skyfly.data.mapper

import com.kom.skyfly.data.model.history.AirlineDomain
import com.kom.skyfly.data.model.history.ArrivalDomain
import com.kom.skyfly.data.model.history.BookingDomain
import com.kom.skyfly.data.model.history.DepartureAirportDomain
import com.kom.skyfly.data.model.history.DepartureDomain
import com.kom.skyfly.data.model.history.DestinationAirportDomain
import com.kom.skyfly.data.model.history.FlightDomain
import com.kom.skyfly.data.model.history.HistoryDomain
import com.kom.skyfly.data.model.history.ItemsHistoryDomain
import com.kom.skyfly.data.model.history.SeatDomain
import com.kom.skyfly.data.model.history.TransactionDetailDomain
import com.kom.skyfly.data.model.history.TransactionDomain
import com.kom.skyfly.data.source.network.model.history.Airline
import com.kom.skyfly.data.source.network.model.history.Arrival
import com.kom.skyfly.data.source.network.model.history.Booking
import com.kom.skyfly.data.source.network.model.history.Departure
import com.kom.skyfly.data.source.network.model.history.DepartureAirport
import com.kom.skyfly.data.source.network.model.history.DestinationAirport
import com.kom.skyfly.data.source.network.model.history.Flight
import com.kom.skyfly.data.source.network.model.history.HistoryResponse
import com.kom.skyfly.data.source.network.model.history.ItemsHistoryResponse
import com.kom.skyfly.data.source.network.model.history.Seat
import com.kom.skyfly.data.source.network.model.history.Transaction
import com.kom.skyfly.data.source.network.model.history.TransactionDetail

fun HistoryResponse?.toHistoryDomain(): HistoryDomain =
    this?.let {
        HistoryDomain(
            data = it.data.toItemsHistoryDomainList(),
            message = it.message.orEmpty(),
            status = it.status ?: false,
            totalItems = it.totalItems ?: 0,
        )
    } ?: HistoryDomain(emptyList(), "", false, 0)

fun List<ItemsHistoryResponse?>?.toItemsHistoryDomainList(): List<ItemsHistoryDomain> =
    this?.mapNotNull { it.toItemsHistoryDomain() } ?: emptyList()

fun ItemsHistoryResponse?.toItemsHistoryDomain(): ItemsHistoryDomain? =
    this?.let {
        ItemsHistoryDomain(
            date = it.date.orEmpty(),
            transactions = it.transactions.toTransactionDomainList(),
        )
    }

fun List<Transaction?>?.toTransactionDomainList(): List<TransactionDomain> = this?.mapNotNull { it.toTransactionDomain() } ?: emptyList()

fun Transaction?.toTransactionDomain(): TransactionDomain? =
    this?.let {
        TransactionDomain(
            booking = it.booking.toBookingDomain(),
            id = it.id.orEmpty(),
            orderId = it.orderId.orEmpty(),
            status = it.status.orEmpty(),
            tax = it.tax ?: 0,
            totalPrice = it.totalPrice ?: 0,
            transactionDetail = it.transactionDetail.toTransactionDetailDomainList(),
            userId = it.userId.orEmpty(),
        )
    }

fun List<TransactionDetail?>?.toTransactionDetailDomainList(): List<TransactionDetailDomain> =
    this?.mapNotNull { it.toTransactionDetailDomain() } ?: emptyList()

fun TransactionDetail?.toTransactionDetailDomain(): TransactionDetailDomain? =
    this?.let {
        it.flight.toFlightDomain()?.let { flight ->
            TransactionDetailDomain(
                citizenship = it.citizenship.orEmpty(),
                dob = it.dob.orEmpty(),
                familyName = it.familyName.orEmpty(),
                flight = flight,
                id = it.id.orEmpty(),
                issuingCountry = it.issuingCountry.orEmpty(),
                name = it.name.orEmpty(),
                passengerCategory = it.passengerCategory.orEmpty(),
                passport = it.passport.orEmpty(),
                seat = it.seat.toSeatDomain(),
                totalPrice = it.totalPrice ?: 0,
                transactionId = it.transactionId.orEmpty(),
                validityPeriod = it.validityPeriod.orEmpty(),
            )
        }
    }

fun Airline?.toAirlineDomain(): AirlineDomain =
    AirlineDomain(
        code = this?.code.orEmpty(),
        id = this?.id.orEmpty(),
        image = this?.image.orEmpty(),
        name = this?.name.orEmpty(),
        terminal = this?.terminal.orEmpty(),
    )

fun Arrival?.toArrivalDomain(): ArrivalDomain =
    ArrivalDomain(
        date = this?.date.orEmpty(),
        time = this?.time.orEmpty(),
    )

fun Booking?.toBookingDomain(): BookingDomain =
    BookingDomain(
        code = this?.code.orEmpty(),
        date = this?.date.orEmpty(),
        time = this?.time.orEmpty(),
    )

fun Departure?.toDepartureDomain(): DepartureDomain =
    DepartureDomain(
        date = this?.date.orEmpty(),
        time = this?.time.orEmpty(),
    )

fun DepartureAirport?.toDepartureAirportDomain(): DepartureAirportDomain =
    DepartureAirportDomain(
        city = this?.city.orEmpty(),
        code = this?.code.orEmpty(),
        continent = this?.continent.orEmpty(),
        country = this?.country.orEmpty(),
        id = this?.id.orEmpty(),
        image = this?.image.orEmpty(),
        name = this?.name.orEmpty(),
    )

fun DestinationAirport?.toDestinationAirportDomain(): DestinationAirportDomain =
    DestinationAirportDomain(
        city = this?.city.orEmpty(),
        code = this?.code.orEmpty(),
        continent = this?.continent.orEmpty(),
        country = this?.country.orEmpty(),
        id = this?.id.orEmpty(),
        image = this?.image.orEmpty(),
        name = this?.name.orEmpty(),
    )

fun Flight?.toFlightDomain(): FlightDomain? =
    this?.airline?.toAirlineDomain()?.let {
        this.arrival?.toArrivalDomain()?.let { arrival ->
            this.departure?.toDepartureDomain()?.let { departure ->
                this.departureAirport?.toDepartureAirportDomain()?.let { departureAirport ->
                    this.destinationAirport?.toDestinationAirportDomain()?.let { destinationAirport ->
                        FlightDomain(
                            airline = it,
                            arrival = arrival,
                            code = this.code.orEmpty(),
                            departure = departure,
                            departureAirport = departureAirport,
                            destinationAirport = destinationAirport,
                            flightDuration = this.flightDuration.orEmpty(),
                            flightPrice = this.flightPrice ?: 0,
                            id = this.id.orEmpty(),
                        )
                    }
                }
            }
        }
    }

fun Seat?.toSeatDomain(): SeatDomain =
    SeatDomain(
        flightId = this?.flightId.orEmpty(),
        id = this?.id.orEmpty(),
        seatNumber = this?.seatNumber.orEmpty(),
        seatPrice = this?.seatPrice ?: 0,
        status = this?.status.orEmpty(),
        type = this?.type.orEmpty(),
    )
