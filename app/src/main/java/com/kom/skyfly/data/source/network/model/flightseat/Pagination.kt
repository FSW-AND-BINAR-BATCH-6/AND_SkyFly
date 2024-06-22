package com.kom.skyfly.data.source.network.model.flightseat

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Pagination(
    @SerializedName("currentPage")
    val currentPage: Int?,
    @SerializedName("nextPage")
    val nextPage: Int?,
    @SerializedName("pageItems")
    val pageItems: Int?,
    @SerializedName("prevPage")
    val prevPage: Any?,
    @SerializedName("totalPage")
    val totalPage: Int?,
)
