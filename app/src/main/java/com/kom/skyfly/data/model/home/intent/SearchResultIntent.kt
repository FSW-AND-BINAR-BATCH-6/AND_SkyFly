package com.kom.skyfly.data.model.home.intent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResultIntent(
    val returnId: String?,
    val departureId: String?,
    val seatClass: String?,
    val adultCount: Int?,
    val childCount: Int?,
    val babyCount: Int?,
    val roundTrip: Boolean?,
) : Parcelable
