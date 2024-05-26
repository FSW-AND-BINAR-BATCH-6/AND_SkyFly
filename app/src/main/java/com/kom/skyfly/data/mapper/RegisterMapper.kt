package com.kom.skyfly.data.mapper

import com.kom.skyfly.data.model.register.Register
import com.kom.skyfly.data.source.network.model.register.RegisterResponse

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
fun RegisterResponse?.toRegister() =
    Register(
        message = this?.message.orEmpty(),
        status = this?.status,
        token = this?.token.orEmpty(),
    )

fun Collection<RegisterResponse>?.toRegisters() = this?.map { it.toRegister() } ?: listOf()
