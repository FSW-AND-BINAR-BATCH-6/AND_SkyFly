package com.kom.skyfly.data.repository.home.airport

import com.kom.skyfly.data.datasource.home.HomeDataSource
import com.kom.skyfly.data.mapper.home.toAirports
import com.kom.skyfly.data.model.home.search.Airport
import com.kom.skyfly.utils.ResultWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AirportRepositoryImpl(
    private val dataSource: HomeDataSource,
) : AirportRepository {
    override fun getAllAirportData(
        page: Int,
        limit: Int,
    ): Flow<ResultWrapper<List<Airport>>> {
        return flow {
            emit(ResultWrapper.Loading())
            val result = dataSource.getAllAirports(page = page, limit = limit).data.toAirports()
            delay(500)
            emit(ResultWrapper.Success(result))
        }
    }
}
