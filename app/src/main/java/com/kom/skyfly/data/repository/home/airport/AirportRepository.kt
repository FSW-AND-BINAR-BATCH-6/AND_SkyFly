package com.kom.skyfly.data.repository.home.airport

import com.kom.skyfly.data.model.home.search.Airport
import com.kom.skyfly.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface AirportRepository {
    fun getAllAirportData(): Flow<ResultWrapper<List<Airport>>>
}
