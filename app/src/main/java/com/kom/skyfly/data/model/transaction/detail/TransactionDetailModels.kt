// FlightSeats.kt
package com.kom.skyfly.data.model.transaction.detail

data class FlightSeats(
    val flightId: String,
    val flightSeatId: String,
    val seatNumber: String,
    val status: String,
    val type: String,
    val price: Int,
)

data class TransactionDetails(
    val citizenship: String,
    val dob: String,
    val familyName: String,
    val flight: Flights,
    val id: String,
    val issuingCountry: String,
    val name: String,
    val passengerCategory: String,
    val passport: String,
    val seat: Seats,
    val totalPrice: Int,
    val transactionId: String,
    val validityPeriod: String,
)

data class Flights(
    val airline: Airlines,
    val arrival: Arrivals,
    val code: String,
    val departure: Departures,
    val departureAirport: DepartureAirports,
    val destinationAirport: DestinationAirports,
    val flightDuration: String,
    val flightPrice: Int,
    val id: String,
)

data class Airlines(
    val code: String,
    val id: String,
    val image: String,
    val name: String,
    val terminal: String,
)

data class Arrivals(
    val date: String,
    val time: String,
)

data class Bookings(
    val code: String,
    val date: String,
    val time: String,
)

data class Departures(
    val date: String,
    val time: String,
)

data class DepartureAirports(
    val city: String,
    val code: String,
    val continent: String,
    val country: String,
    val id: String,
    val image: String,
    val name: String,
)

data class DestinationAirports(
    val city: String,
    val code: String,
    val continent: String,
    val country: String,
    val id: String,
    val image: String,
    val name: String,
)

data class Seats(
    val flightId: String,
    val id: String,
    val seatNumber: String,
    val seatPrice: Int,
    val status: String,
    val type: String,
)

data class Datas(
    val booking: Bookings?,
    val id: String,
    val orderId: String,
    val status: String,
    val tax: Int,
    val totalPrice: Int,
    val transactionDetails: List<TransactionDetails?>?,
    val userId: String,
)

data class TransactionDetailResponses(
    val data: Datas?,
    val message: String,
    val status: Boolean,
)
