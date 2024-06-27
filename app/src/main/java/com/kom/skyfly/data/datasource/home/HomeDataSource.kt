package com.kom.skyfly.data.datasource.home

import com.kom.skyfly.data.model.home.seat_class.SeatClassHome
import com.kom.skyfly.data.source.network.model.home.airport.AirportResponse
import com.kom.skyfly.data.source.network.model.home.favourite_destination.FavouriteDestinationResponse
import com.kom.skyfly.data.source.network.model.home.flight.FlightResponse
import com.kom.skyfly.data.source.network.model.home.flight_detail.FlightDetailResponse

interface HomeDataSource {
    suspend fun getAllAirports(city: String? = null): AirportResponse

    suspend fun getAllFlight(
        search: String? = null,
        page: Int,
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String,
        seatClass: String,
        limit: Int? = 20,
        returnDate: String?,
        arrivalDate: String? = null,
        adult: Int? = 1,
        children: Int? = 0,
        baby: Int? = 0,
    ): FlightResponse

    suspend fun getDetailFlight(
        id: String,
        seatClass: String,
    ): FlightDetailResponse

    fun getSeatClassData(): List<SeatClassHome>

    suspend fun getDestinationFavourites(): FavouriteDestinationResponse
}
