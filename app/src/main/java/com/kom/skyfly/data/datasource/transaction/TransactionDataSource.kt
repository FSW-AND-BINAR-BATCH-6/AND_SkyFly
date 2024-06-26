package com.kom.skyfly.data.datasource.transaction

import com.kom.skyfly.data.source.network.model.paymentstatus.PaymentStatusResponse
import com.kom.skyfly.data.source.network.model.transaction.cancel.CancelTransactionResponse
import com.kom.skyfly.data.source.network.model.transaction.detail.TransactionDetailResponse
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

    suspend fun getTransactionById(id: String): TransactionDetailResponse

    suspend fun getPaymentStatus(id: String): PaymentStatusResponse

    suspend fun cancelTransaction(id: String): CancelTransactionResponse
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

    override suspend fun getTransactionById(id: String): TransactionDetailResponse {
        return service.getTransactionById(id)
    }

    override suspend fun getPaymentStatus(id: String): PaymentStatusResponse {
        return service.getPaymentStatus(id)
    }

    override suspend fun cancelTransaction(id: String): CancelTransactionResponse {
        return service.cancelTransaction(id)
    }
}
