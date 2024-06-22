package com.kom.skyfly.data.source.network.model.notification

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
    val prevPage: Int?,
    @SerializedName("totalPage")
    val totalPage: Int?,
)
