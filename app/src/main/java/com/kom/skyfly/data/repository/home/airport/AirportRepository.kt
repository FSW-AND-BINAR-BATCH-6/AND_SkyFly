package com.kom.skyfly.data.repository.home.airport

import com.kom.skyfly.data.model.home.search.Airport
import com.kom.skyfly.data.model.home.search_history.SearchDestinationHistory
import com.kom.skyfly.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface AirportRepository {
    fun getAllAirportData(city: String? = null): Flow<ResultWrapper<List<Airport>>>

    fun createSearchDestinationHistory(searchDestinationHistory: String): Flow<ResultWrapper<Boolean>>

    fun deleteSearchDestinationHistory(item: SearchDestinationHistory): Flow<ResultWrapper<Boolean>>

    fun deleteAllSearchDestinationHistory(): Flow<ResultWrapper<Unit>>

    fun getUserSearchDestinationHistory(): Flow<ResultWrapper<List<SearchDestinationHistory>>>
}
