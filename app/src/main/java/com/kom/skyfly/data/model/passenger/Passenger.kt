package com.kom.skyfly.data.model.passenger

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
data class PassengerData(
    val title: String?,
    val fullName: String?,
    val familyName: String? = null,
    val dateOfBirth: String?,
    val nationality: String?,
    val idOrPassport: String?,
    val idOrPassportValidUntil: String?,
)
