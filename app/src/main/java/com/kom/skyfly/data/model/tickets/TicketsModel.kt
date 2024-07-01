package com.kom.skyfly.data.model.tickets

import androidx.annotation.Keep

@Keep
data class TicketsModel(
    val data: List<ItemsTicketsModel?>,
    val message: String?,
    val status: Boolean?,
)
