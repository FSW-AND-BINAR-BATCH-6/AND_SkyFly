package com.kom.skyfly.data.model.home.destination_favourite

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/

@Parcelize
data class DestinationFavourite(
    val id: String?,
    val departureDate: String?,
    val departureCity: String?,
    val arrivalDate: String?,
    val arrivalCity: String?,
    val airline: String?,
    val price: Int?,
    val img: String?,
) : Parcelable
