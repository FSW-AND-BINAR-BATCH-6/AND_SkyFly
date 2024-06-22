package com.kom.skyfly.data.source.network.model.transaction.request

import com.google.gson.annotations.SerializedName

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
data class TransactionRequest(
    @SerializedName("orderer")
    val orderer: Bookers,
    val passengers: List<Passenger>,
)
