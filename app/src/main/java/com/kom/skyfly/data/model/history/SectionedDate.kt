package com.kom.skyfly.data.model.history

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SectionedDate(
    val date: String,
    val data: List<Data>,
) : Parcelable
