package com.kom.skyfly.data.mapper

import com.kom.skyfly.data.model.tickets.*
import com.kom.skyfly.data.source.network.model.tickets.*

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
fun TicketsResponse?.toTicketsModel(): TicketsModel =
    this?.let {
        TicketsModel(
            data = it.data.toItemsTicketsModelList(),
            message = it.message.orEmpty(),
            status = it.status ?: false,
        )
    } ?: TicketsModel(emptyList(), "", false)

fun List<ItemsTicketsResponse?>?.toItemsTicketsModelList(): List<ItemsTicketsModel> =
    this?.mapNotNull { it.toItemsTicketsModel() } ?: emptyList()

fun ItemsTicketsResponse?.toItemsTicketsModel(): ItemsTicketsModel? =
    this?.let {
        ItemsTicketsModel(
            code = it.code.orEmpty(),
            flight = it.flight.toFlightModel(),
            flightId = it.flightId.orEmpty(),
            id = it.id.orEmpty(),
            seatId = it.seatId.orEmpty(),
            ticketTransaction = it.ticketTransaction.toTicketTransactionModel(),
            ticketTransactionId = it.ticketTransactionId.orEmpty(),
            user = it.user.toUserModel(),
            userId = it.userId.orEmpty(),
        )
    }

fun Flight?.toFlightModel(): FlightModel? =
    this?.let {
        FlightModel(
            arrivalDate = it.arrivalDate.orEmpty(),
            capacity = it.capacity ?: 0,
            code = it.code.orEmpty(),
            departureAirportId = it.departureAirportId.orEmpty(),
            departureDate = it.departureDate.orEmpty(),
            destinationAirportId = it.destinationAirportId.orEmpty(),
            discount = it.discount.orEmpty(),
            facilities = it.facilities.orEmpty(),
            id = it.id.orEmpty(),
            planeId = it.planeId.orEmpty(),
            price = it.price ?: 0,
            transitAirportId = it.transitAirportId.orEmpty(),
            transitArrivalDate = it.transitArrivalDate.orEmpty(),
            transitDepartureDate = it.transitDepartureDate.orEmpty(),
        )
    }

fun Seat?.toSeatModel(): SeatModel? =
    this?.let {
        SeatModel(
            flightId = it.flightId.orEmpty(),
            id = it.id.orEmpty(),
            price = it.price ?: 0,
            seatNumber = it.seatNumber.orEmpty(),
            status = it.status.orEmpty(),
            type = it.type.orEmpty(),
        )
    }

fun TicketTransaction?.toTicketTransactionModel(): TicketTransactionModel? =
    this?.let {
        TicketTransactionModel(
            bookingCode = it.bookingCode.orEmpty(),
            bookingDate = it.bookingDate.orEmpty(),
            id = it.id.orEmpty(),
            orderId = it.orderId.orEmpty(),
            status = it.status.orEmpty(),
            tax = it.tax ?: 0,
            totalPrice = it.totalPrice ?: 0,
            transactionDetail = it.transactionDetail.toTransactionDetailModelList(),
            userId = it.userId.orEmpty(),
        )
    }

fun List<TransactionDetail?>?.toTransactionDetailModelList(): List<TransactionDetailModel> =
    this?.mapNotNull { it.toTransactionDetailModel() } ?: emptyList()

fun TransactionDetail?.toTransactionDetailModel(): TransactionDetailModel? =
    this?.let {
        TransactionDetailModel(
            citizenship = it.citizenship.orEmpty(),
            dob = it.dob.orEmpty(),
            familyName = it.familyName.orEmpty(),
            flightId = it.flightId.orEmpty(),
            id = it.id.orEmpty(),
            issuingCountry = it.issuingCountry.orEmpty(),
            name = it.name.orEmpty(),
            passport = it.passport.orEmpty(),
            price = it.price ?: 0,
            seatId = it.seatId.orEmpty(),
            transactionId = it.transactionId.orEmpty(),
            type = it.type.orEmpty(),
            validityPeriod = it.validityPeriod.orEmpty(),
            seat = it.seat.toSeatModel(),
        )
    }

fun Auth?.toAuthModel(): AuthModel? =
    this?.let {
        AuthModel(
            email = it.email.orEmpty(),
            id = it.id.orEmpty(),
            isVerified = it.isVerified ?: false,
        )
    }

fun User?.toUserModel(): UserModel? =
    this?.let {
        UserModel(
            auth = it.auth.toAuthModel(),
            familyName = it.familyName.orEmpty(),
            id = it.id.orEmpty(),
            name = it.name.orEmpty(),
            phoneNumber = it.phoneNumber.orEmpty(),
            role = it.role.orEmpty(),
        )
    }
