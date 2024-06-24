package com.kom.skyfly.data.model.history.new

data class AirlineDomain(
    val code: String,
    val id: String,
    val image: String,
    val name: String,
    val terminal: String,
)

data class ArrivalDomain(val date: String, val time: String)

data class BookingDomain(val code: String, val date: String, val time: String)

data class DepartureDomain(val date: String, val time: String)

data class DepartureAirportDomain(
    val city: String,
    val code: String,
    val continent: String,
    val country: String,
    val id: String,
    val image: String,
    val name: String,
)

data class DestinationAirportDomain(
    val city: String,
    val code: String,
    val continent: String,
    val country: String,
    val id: String,
    val image: String,
    val name: String,
)

data class FlightDomain(
    val airline: AirlineDomain,
    val arrival: ArrivalDomain,
    val code: String,
    val departure: DepartureDomain,
    val departureAirport: DepartureAirportDomain,
    val destinationAirport: DestinationAirportDomain,
    val flightDuration: String,
    val flightPrice: Int,
    val id: String,
)

data class ItemsHistoryDomain(val date: String, val transactions: List<TransactionDomain>)

data class SeatDomain(
    val flightId: String,
    val id: String,
    val seatNumber: String,
    val seatPrice: Int,
    val status: String,
    val type: String,
)

data class TransactionDomain(
    val booking: BookingDomain,
    val id: String,
    val orderId: String,
    val status: String,
    val tax: Int,
    val totalPrice: Int,
    val transactionDetail: List<TransactionDetailDomain>,
    val userId: String,
)

data class TransactionDetailDomain(
    val citizenship: String,
    val dob: String,
    val familyName: String,
    val flight: FlightDomain,
    val id: String,
    val issuingCountry: String,
    val name: String,
    val passengerCategory: String,
    val passport: String,
    val seat: SeatDomain,
    val totalPrice: Int,
    val transactionId: String,
    val validityPeriod: String,
)

data class HistoryDomain(
    val data: List<ItemsHistoryDomain>,
    val message: String,
    val status: Boolean,
    val totalItems: Int,
)
