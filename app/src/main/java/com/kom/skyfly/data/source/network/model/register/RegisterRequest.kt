package com.kom.skyfly.data.source.network.model.register

import com.google.gson.annotations.SerializedName

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
data class RegisterRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("password")
    val password: String,
)
