package com.kom.skyfly.data.source.network.model.forgetpassword

import com.google.gson.annotations.SerializedName

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
data class ForgetPasswordRequest(
    @SerializedName("email")
    val email: String,
)
