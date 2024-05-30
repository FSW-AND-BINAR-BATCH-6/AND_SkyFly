package com.kom.skyfly.data.datasource.history

import com.kom.skyfly.data.model.history.Data
import com.kom.skyfly.data.model.history.SectionedDate

interface HistoryDataSource {
    fun getHistoryData(): List<SectionedDate>
}

class HistoryDataSourceImpl() : HistoryDataSource {
    override fun getHistoryData(): List<SectionedDate> {
        return mutableListOf(
            SectionedDate(
                date = "Maret 2020",
                data =
                    listOf(
                        Data(
                            status = "Paid",
                            departureLocation = "Jakarta",
                            departureDate = "2020-03-01",
                            departureTime = "08:00",
                            flightDuration = "2h 30m",
                            destination = "Bali",
                            arrivalDate = "2020-03-01",
                            arrivalTime = "10:30",
                            bookingCode = "AB123",
                            flightClass = "Economy",
                            total = "IDR 2.550.000",
                        ),
                        Data(
                            status = "Cancelled",
                            departureLocation = "Surabaya",
                            departureDate = "2020-03-15",
                            departureTime = "14:00",
                            flightDuration = "1h 20m",
                            destination = "Yogyakarta",
                            arrivalDate = "2020-03-15",
                            arrivalTime = "15:20",
                            bookingCode = "CD456",
                            flightClass = "Business",
                            total = "IDR 2.000.000",
                        ),
                        Data(
                            status = "Unpaid",
                            departureLocation = "Jakarta",
                            departureDate = "2020-03-01",
                            departureTime = "08:00",
                            flightDuration = "2h 30m",
                            destination = "Bali",
                            arrivalDate = "2020-03-01",
                            arrivalTime = "10:30",
                            bookingCode = "AB123",
                            flightClass = "Economy",
                            total = "IDR 1.500.000",
                        ),
                        Data(
                            status = "Cancelled",
                            departureLocation = "Surabaya",
                            departureDate = "2020-03-15",
                            departureTime = "14:00",
                            flightDuration = "1h 20m",
                            destination = "Yogyakarta",
                            arrivalDate = "2020-03-15",
                            arrivalTime = "15:20",
                            bookingCode = "CD456",
                            flightClass = "Business",
                            total = "IDR 1.000.000",
                        ),
                    ),
            ),
            SectionedDate(
                date = "April 2020",
                data =
                    listOf(
                        Data(
                            status = "Unpaid",
                            departureLocation = "Bandung",
                            departureDate = "2020-04-05",
                            departureTime = "09:00",
                            flightDuration = "3h 15m",
                            destination = "Medan",
                            arrivalDate = "2020-04-05",
                            arrivalTime = "12:15",
                            bookingCode = "EF789",
                            flightClass = "First",
                            total = "IDR 2.700.000",
                        ),
                        Data(
                            status = "Cancelled",
                            departureLocation = "Surabaya",
                            departureDate = "2020-03-15",
                            departureTime = "14:00",
                            flightDuration = "1h 20m",
                            destination = "Yogyakarta",
                            arrivalDate = "2020-03-15",
                            arrivalTime = "15:20",
                            bookingCode = "CD456",
                            flightClass = "Business",
                            total = "IDR 2.000.000",
                        ),
                        Data(
                            status = "Paid",
                            departureLocation = "Jakarta",
                            departureDate = "2020-03-01",
                            departureTime = "08:00",
                            flightDuration = "2h 30m",
                            destination = "Bali",
                            arrivalDate = "2020-03-01",
                            arrivalTime = "10:30",
                            bookingCode = "AB123",
                            flightClass = "Economy",
                            total = "IDR 1.500.000",
                        ),
                        Data(
                            status = "Cancelled",
                            departureLocation = "Surabaya",
                            departureDate = "2020-03-15",
                            departureTime = "14:00",
                            flightDuration = "1h 20m",
                            destination = "Yogyakarta",
                            arrivalDate = "2020-03-15",
                            arrivalTime = "15:20",
                            bookingCode = "CD456",
                            flightClass = "Business",
                            total = "IDR 2.500.000",
                        ),
                    ),
            ),
        )
    }
}
