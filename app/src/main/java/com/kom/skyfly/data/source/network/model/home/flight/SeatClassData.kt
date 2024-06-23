package com.kom.skyfly.data.source.network.model.home.flight

import com.google.gson.annotations.SerializedName

data class SeatClassData(
    @SerializedName("seatClass")
    val seatClassName: String?,
    @SerializedName("seatPrice")
    val seatPrice: Int?,
)
