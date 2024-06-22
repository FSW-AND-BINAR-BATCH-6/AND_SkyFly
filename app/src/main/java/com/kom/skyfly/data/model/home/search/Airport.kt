package com.kom.skyfly.data.model.home.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Airport(
    val id: String,
    val name: String,
    val code: String,
    val country: String,
    val city: String,
) : Parcelable
