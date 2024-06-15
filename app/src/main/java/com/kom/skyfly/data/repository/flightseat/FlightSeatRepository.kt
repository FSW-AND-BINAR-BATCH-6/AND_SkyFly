package com.kom.skyfly.data.repository.flightseat

import com.kom.skyfly.data.datasource.flightseat.FlightSeatDataSource
import com.kom.skyfly.data.mapper.toFlightSeats
import com.kom.skyfly.data.source.network.model.flightseat.FlightSeatResponse
import com.kom.skyfly.utils.ResultWrapper
import com.kom.skyfly.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface FlightSeatRepository {
    fun getAllFlightSeat(id: String): Flow<ResultWrapper<FlightSeatResponse>>

    fun getFlightSeat(id: String): Flow<ResultWrapper<Pair<String, List<String>>>>
}

class FlightSeatRepositoryImpl(private val datasource: FlightSeatDataSource) :
    FlightSeatRepository {
    override fun getAllFlightSeat(id: String): Flow<ResultWrapper<FlightSeatResponse>> {
        return proceedFlow { datasource.getAllFlightSeat(id) }
    }

    override fun getFlightSeat(id: String): Flow<ResultWrapper<Pair<String, List<String>>>> {
        return proceedFlow {
            val flightSeats = datasource.getAllFlightSeat(id).data.toFlightSeats()

            val chunkedData =
                flightSeats.chunked(6).map { chunk ->
                    val seatStatusStr =
                        chunk.map { mapSeatStatus(it.status) }
                            .chunked(3)
                            .map { it.joinToString("") }
                            .joinToString("_")

                    val centerIndex = if (chunk.size > 3) 3 else chunk.size
                    val seatLabel = chunk.map { it.seatNumber ?: "" }.toMutableList()
                    seatLabel.add(centerIndex, " ")
                    seatLabel.add(0, "/")
                    Pair(seatStatusStr, seatLabel)
                }

            val status = chunkedData.map { it.first }.joinToString("/", prefix = "/")
            val title = chunkedData.map { it.second }.flatten()
            println(status)
            println(title)
            Pair(status, title)
        }
    }

    private fun mapSeatStatus(seat: String?): Char {
        return when (seat) {
            "available" -> 'A'
            "occupied" -> 'U'
            "booked" -> 'R'
            else -> ' '
        }
    }
}
