package com.kom.skyfly.data.model.destinationfavorite

import java.util.UUID

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
data class DestinationFavorite(
    val id: String? = UUID.randomUUID().toString(),
    val origin: String?,
    val destination: String?,
    val airline: String?,
    val dateRange: String?,
    val price: String?,
    val isLimited: Boolean?,
    val discount: String?,
    val imageUrl: String?,
)
