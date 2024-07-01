package com.kom.skyfly.data.datasource.home

import com.kom.skyfly.data.model.home.filter.Filter
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

    override suspend fun getAllFlight(
        search: String?,
        page: Int,
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String,
        seatClass: String?,
        limit: Int?,
        returnDate: String?,
        arrivalDate: String?,
        adult: Int?,
        children: Int?,
        baby: Int?,
        sort: String?,
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
            sort = sort,
        )
    }

    override fun getFilterData(): List<Filter> {
        return listOf(
            Filter(
                filterName = "shortest-duration",
            ),
            Filter(
                filterName = "cheapest-flight",
            ),
            Filter(
                filterName = "earliest-departure",
            ),
            Filter(
                filterName = "latest-departure",
            ),
            Filter(
                filterName = "earliest-arrival",
            ),
            Filter(
                filterName = "latest-arrival",
            ),
        )
    }

    override suspend fun getDetailFlight(
        id: String,
        seatClass: String?,
    ): FlightDetailResponse {
        return service.getDetailFlightById(id = id, seatClass = seatClass)
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
                seatClassName = "FIRST",
            ),
        )
    }

    override suspend fun getDestinationFavourites(): FavouriteDestinationResponse {
        return service.getDestinationFavourite()
    }
}
