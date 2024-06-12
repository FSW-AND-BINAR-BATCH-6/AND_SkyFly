package com.kom.skyfly.data.source.network.model.home.airport

import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("totalPage")
    val totalPage: Int,
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("pageItems")
    val pageItems: Int,
    @SerializedName("nextPage")
    val nextPage: Int,
    @SerializedName("prevPage")
    val prevPage: Int,
)
