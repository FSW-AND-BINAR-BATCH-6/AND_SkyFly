package com.kom.skyfly.data.model.tickets

import androidx.annotation.Keep

@Keep
data class AuthModel(
    val email: String?,
    val id: String?,
    val isVerified: Boolean?,
)
