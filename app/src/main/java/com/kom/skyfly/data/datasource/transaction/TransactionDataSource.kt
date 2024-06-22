package com.kom.skyfly.data.datasource.transaction

import com.kom.skyfly.data.source.network.model.transaction.request.TransactionRequest
import com.kom.skyfly.data.source.network.model.transaction.response.TransactionResponse
import com.kom.skyfly.data.source.network.services.SkyFlyApiService

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface TransactionDataSource {
    suspend fun createTransaction(
        flightId: String,
        adult: Int,
        child: Int,
        baby: Int,
        transactionRequest: TransactionRequest,
    ): TransactionResponse
//    suspend fun getTransactionData(transactionId: String): TransactionResponse
}

class TransactionDataSourceImpl(private val service: SkyFlyApiService) : TransactionDataSource {
    override suspend fun createTransaction(
        flightId: String,
        adult: Int,
        child: Int,
        baby: Int,
        transactionRequest: TransactionRequest,
    ): TransactionResponse {
        return service.createTransaction(flightId, adult, child, baby, transactionRequest)
    }

//    override suspend fun getTransactionData(transactionId: String): TransactionResponse {
//        return service.getTransactionData(transactionId)
//    }
}
