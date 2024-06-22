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
data class FlightData(
    val flightSeat: List<FlightSeat>,
    val seatLabelling: Pair<String, List<String>>,
)

interface FlightSeatRepository {
    fun getAllFlightSeat(id: String): Flow<ResultWrapper<List<FlightSeat>>>

    fun getFlightSeat(id: String): Flow<ResultWrapper<FlightData>>

    fun getFlightSeatPrice(id: String): Flow<ResultWrapper<Pair<List<String?>, List<Int?>>>>
}

class FlightSeatRepositoryImpl(private val datasource: FlightSeatDataSource) :
    FlightSeatRepository {
    override fun getAllFlightSeat(id: String): Flow<ResultWrapper<List<FlightSeat>>> {
        return proceedFlow { datasource.getAllFlightSeat(id).data.toFlightSeats() }
    }

    override fun getFlightSeat(id: String): Flow<ResultWrapper<FlightData>> {
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
            FlightData(flightSeats, Pair(status, title))
        }
    }

    override fun getFlightSeatPrice(id: String): Flow<ResultWrapper<Pair<List<String?>, List<Int?>>>> {
        return proceedFlow {
            val flightSeat = datasource.getAllFlightSeat(id).data.toFlightSeats()
            val price =
                flightSeat.map {
                    it.price
                }
            val seatId =
                flightSeat.map {
                    it.flightSeatId
                }
            Pair(seatId, price)
        }
    }

    private fun mapSeatStatus(seat: String?): Char {
        return when (seat) {
            "AVAILABLE" -> 'A'
            "OCCUPIED" -> 'R'
            "BOOKED" -> 'U'
            else -> ' '
        }
    }
}
