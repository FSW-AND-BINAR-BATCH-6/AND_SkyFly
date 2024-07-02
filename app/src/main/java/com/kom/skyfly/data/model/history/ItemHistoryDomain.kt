package com.kom.skyfly.data.model.history

data class ItemsHistoryDomain(
    val date: String,
    val transactions: List<TransactionDomain>,
)
