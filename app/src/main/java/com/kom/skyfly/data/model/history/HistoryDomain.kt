package com.kom.skyfly.data.model.history

data class HistoryDomain(
    val data: List<ItemsHistoryDomain>,
    val message: String,
    val status: Boolean,
    val totalItems: Int,
)
