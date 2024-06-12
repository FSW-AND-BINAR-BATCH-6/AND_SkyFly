package com.kom.skyfly.data.repository.flightseat

import com.kom.skyfly.data.datasource.flightseat.FlightSeatDataSource
import com.kom.skyfly.data.mapper.toFlightSeats
import com.kom.skyfly.data.model.flightseat.FlightSeat
import com.kom.skyfly.utils.ResultWrapper
import com.kom.skyfly.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface FlightSeatRepository {
    fun getAllFlightSeat(id: String): Flow<ResultWrapper<List<FlightSeat>>>
}

class FlightSeatRepositoryImpl(private val datasource: FlightSeatDataSource) :
    FlightSeatRepository {
    override fun getAllFlightSeat(id: String): Flow<ResultWrapper<List<FlightSeat>>> {
        return proceedFlow { datasource.getAllFlightSeat(id).data.toFlightSeats() }
    }
}
