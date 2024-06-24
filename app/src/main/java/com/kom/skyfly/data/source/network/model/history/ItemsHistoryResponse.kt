package com.kom.skyfly.data.source.network.model.history

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ItemsHistoryResponse(
    @SerializedName("date")
    val date: String?,
    @SerializedName("transactions")
    val transactions: List<Transaction?>?,
)
