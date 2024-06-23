package com.kom.skyfly.presentation.checkout.chooseseat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.model.flightseat.FlightSeat
import com.kom.skyfly.data.model.passenger.PassengerData
import com.kom.skyfly.data.repository.flightseat.FlightSeatRepository
import com.kom.skyfly.data.repository.transaction.TransactionRepository
import com.kom.skyfly.data.source.network.model.transaction.request.Passenger
import com.kom.skyfly.data.source.network.model.transaction.request.TransactionRequest
import kotlinx.coroutines.Dispatchers

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class ChooseSeatViewModel(
    private val flightSeatRepository: FlightSeatRepository,
    private val transactionRepository: TransactionRepository,
) : ViewModel() {
    private val seatList = mutableListOf<FlightSeat>()
    private val selectedSeatList = mutableListOf<FlightSeat>()

    fun setSeatList(data: List<FlightSeat>) {
        seatList.clear()
        seatList.addAll(data)
    }

    fun setSelectedSeatList(selectedIdx: List<Int>) {
        selectedSeatList.clear()
        val selectedSeat = seatList.filterIndexed { index, flightSeat -> selectedIdx.contains(index) }
        selectedSeatList.addAll(selectedSeat)
    }

    fun mapPassengersToSeat(passengers: List<PassengerData>): List<Passenger> {
        val mergedList = selectedSeatList.zip(passengers)
        return mergedList.map { (seat, passenger) ->
            passenger.copy().apply {
                price = seat.price
                seatId = seat.flightSeatId
            }
        }.mapIndexed { index, passenger ->
            Passenger(
                type = passenger.type,
                title = passenger.title,
                fullName = passenger.fullName,
                dob = "${passenger.dob} 10:00:00",
                validityPeriod = "${passenger.validityPeriod} 10:00:00",
                familyName = passenger.familyName,
                citizenship = passenger.citizenship,
                passport = passenger.passport,
                issuingCountry = "Indonesia",
                price = passenger.price,
                quantity = passenger.quantity,
                seatId = passenger.seatId,
            )
        }
    }

    fun getAllFlightSeat(idFlight: String) = flightSeatRepository.getAllFlightSeat(idFlight).asLiveData(Dispatchers.IO)

    fun getFlightSeat(idFlight: String) = flightSeatRepository.getFlightSeat(idFlight).asLiveData(Dispatchers.IO)

    fun getFlightPrice(idFlight: String) = flightSeatRepository.getFlightSeatPrice(idFlight).asLiveData(Dispatchers.IO)

    fun createTransaction(
        flightId: String,
        adult: Int,
        child: Int,
        baby: Int,
        transactionRequest: TransactionRequest,
    ) = transactionRepository.createTransaction(flightId, adult, child, baby, transactionRequest)
        .asLiveData(Dispatchers.IO)
}
