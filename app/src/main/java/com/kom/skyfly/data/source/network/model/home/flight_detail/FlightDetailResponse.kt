package com.kom.skyfly.data.source.network.model.home.flight_detail

import com.google.gson.annotations.SerializedName

data class FlightDetailResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: FlightDetailData,
)
