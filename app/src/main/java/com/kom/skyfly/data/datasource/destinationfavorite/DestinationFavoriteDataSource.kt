package com.kom.skyfly.data.datasource.destinationfavorite

import com.kom.skyfly.data.model.destinationfavorite.DestinationFavorite

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface DestinationFavoriteDataSource {
    fun getAllDestinationFavorite(): List<DestinationFavorite>
}

class DestinationFavoriteDataSourceImpl() : DestinationFavoriteDataSource {
    override fun getAllDestinationFavorite(): List<DestinationFavorite> {
        return mutableListOf(
            DestinationFavorite(
                origin = "Jakarta",
                destination = "Bangkok",
                airline = "AirAsia",
                dateRange = "20 - 30 Maret 2023",
                price = "IDR 950.000",
                isLimited = false,
                discount = null,
                imageUrl = "https://images.unsplash.com/photo-1508009603885-50cf7c579365?q=80&w=2850&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            ),
            DestinationFavorite(
                origin = "Jakarta",
                destination = "Sydney",
                airline = "AirAsia",
                dateRange = "5 - 25 Maret 2023",
                price = "IDR 3.650.000",
                isLimited = false,
                discount = "50% OFF",
                imageUrl = "https://images.unsplash.com/photo-1506973035872-a4ec16b8e8d9?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            ),
            DestinationFavorite(
                origin = "Jakarta",
                destination = "Tokyo",
                airline = "Garuda Indonesia",
                dateRange = "1 - 10 April 2023",
                price = "IDR 4.500.000",
                isLimited = true,
                discount = null,
                imageUrl = "https://images.unsplash.com/photo-1513407030348-c983a97b98d8?q=80&w=2944&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            ),
            DestinationFavorite(
                origin = "Jakarta",
                destination = "Seoul",
                airline = "Korean Air",
                dateRange = "15 - 25 April 2023",
                price = "IDR 3.800.000",
                isLimited = false,
                discount = "30% OFF",
                imageUrl = "https://images.unsplash.com/photo-1506816561089-5cc37b3aa9b0?q=80&w=3075&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            ),
            DestinationFavorite(
                origin = "Jakarta",
                destination = "Paris",
                airline = "Air France",
                dateRange = "5 - 15 Mei 2023",
                price = "IDR 8.000.000",
                isLimited = true,
                discount = null,
                imageUrl = "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?q=80&w=2946&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            ),
            DestinationFavorite(
                origin = "Jakarta",
                destination = "New York",
                airline = "American Airlines",
                dateRange = "10 - 20 Juni 2023",
                price = "IDR 10.000.000",
                isLimited = false,
                discount = "40% OFF",
                imageUrl = "https://images.unsplash.com/photo-1602828889956-45ec6cee6758?q=80&w=2832&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            ),
            DestinationFavorite(
                origin = "Jakarta",
                destination = "London",
                airline = "British Airways",
                dateRange = "20 - 30 Juni 2023",
                price = "IDR 9.000.000",
                isLimited = true,
                discount = null,
                imageUrl = "https://images.unsplash.com/photo-1513635269975-59663e0ac1ad?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            ),
        )
    }
}
