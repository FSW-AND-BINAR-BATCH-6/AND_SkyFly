package com.kom.skyfly.data.model.tickets

import androidx.annotation.Keep

@Keep
data class UserModel(
    val auth: AuthModel?,
    val familyName: String?,
    val id: String?,
    val name: String?,
    val phoneNumber: String?,
    val role: String?,
)
