package com.kom.skyfly.data.model.favourite

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class FavouriteFlight(
    var id: String = UUID.randomUUID().toString(),
    var imgUrl: String,
    var source: String,
    var destination: String,
    var priceStart: Double,
    var date: String,
    var promotion: String,
) : Parcelable
