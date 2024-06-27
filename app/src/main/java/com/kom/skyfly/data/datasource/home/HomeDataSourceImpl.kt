package com.kom.skyfly.data.datasource.home

import com.kom.skyfly.data.model.home.seat_class.SeatClassHome
import com.kom.skyfly.data.source.network.model.home.airport.AirportResponse
import com.kom.skyfly.data.source.network.model.home.favourite_destination.FavouriteDestinationResponse
import com.kom.skyfly.data.source.network.model.home.flight.FlightResponse
import com.kom.skyfly.data.source.network.model.home.flight_detail.FlightDetailResponse
import com.kom.skyfly.data.source.network.services.SkyFlyApiService

class HomeDataSourceImpl(
    private val service: SkyFlyApiService,
) : HomeDataSource {
    override suspend fun getAllAirports(city: String?): AirportResponse {
        return service.getAllAirports(city = city)
    }

    override fun getSeatClassData(): List<SeatClassHome> {
        return mutableListOf(
            SeatClassHome(
                seatClassName = "ECONOMY",
            ),
            SeatClassHome(
                seatClassName = "BUSINESS",
            ),
            SeatClassHome(
                seatClassName = "FIRST CLASS",
            ),
        )
    }

    override suspend fun getDestinationFavourites(): FavouriteDestinationResponse {
        return service.getDestinationFavourite()
    }

    override suspend fun getAllFlight(
        search: String?,
        page: Int,
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String,
        seatClass: String,
        limit: Int?,
        returnDate: String?,
        arrivalDate: String?,
        adult: Int?,
        children: Int?,
        baby: Int?,
    ): FlightResponse {
        return service.getAllFlights(
            search = search,
            page = page,
            departureAirport = departureAirport,
            arrivalAirport = arrivalAirport,
            returnDate = returnDate,
            departureDate = departureDate,
            seatClass = seatClass,
            adult = adult,
            children = children,
            baby = baby,
        )
    }

    override suspend fun getDetailFlight(
        id: String,
        seatClass: String,
    ): FlightDetailResponse {
        return service.getDetailFlightById(id = id, seatClass = seatClass)
    }
}
