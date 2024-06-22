package com.kom.skyfly.data.model.passenger

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
@Parcelize
data class PassengerData(
    val type: String?,
    val title: String?,
    val fullName: String?,
    val dob: String?,
    val validityPeriod: String?,
    val familyName: String?,
    val citizenship: String?,
    val passport: String?,
    var price: Int?,
    val quantity: Int?,
    var seatId: String?,
) : Parcelable
