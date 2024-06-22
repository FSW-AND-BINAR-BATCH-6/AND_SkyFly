package com.kom.skyfly.data.source.network.model.home.flight

import com.google.gson.annotations.SerializedName

data class PlaneData(
    @SerializedName("name")
    val name: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("terminal")
    val terminal: String,
)
