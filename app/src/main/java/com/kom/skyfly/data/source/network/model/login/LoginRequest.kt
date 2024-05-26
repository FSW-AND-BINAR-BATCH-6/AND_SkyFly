package com.kom.skyfly.data.source.network.model.login

import com.google.gson.annotations.SerializedName

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)
