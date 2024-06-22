package com.kom.skyfly.data.mapper.home

import com.kom.skyfly.data.model.home.search.Airport
import com.kom.skyfly.data.source.network.model.home.airport.AirportData

fun AirportData?.toAirport() =
    Airport(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        code = this?.code.orEmpty(),
        country = this?.country.orEmpty(),
        city = this?.city.orEmpty(),
    )

fun Collection<AirportData>?.toAirports() = this?.map { it.toAirport() } ?: listOf()
