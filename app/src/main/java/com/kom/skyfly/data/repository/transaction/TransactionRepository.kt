package com.kom.skyfly.data.repository.transaction

import com.kom.skyfly.data.datasource.transaction.TransactionDataSource
import com.kom.skyfly.data.source.network.model.transaction.request.TransactionRequest
import com.kom.skyfly.data.source.network.model.transaction.response.TransactionResponse
import com.kom.skyfly.utils.ResultWrapper
import com.kom.skyfly.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface TransactionRepository {
    fun createTransaction(
        flightId: String,
        adult: Int,
        child: Int,
        baby: Int,
        transactionRequest: TransactionRequest,
    ): Flow<ResultWrapper<TransactionResponse>>
}

class TransactionRepositoryImpl(private val datasource: TransactionDataSource) :
    TransactionRepository {
    override fun createTransaction(
        flightId: String,
        adult: Int,
        child: Int,
        baby: Int,
        transactionRequest: TransactionRequest,
    ): Flow<ResultWrapper<TransactionResponse>> {
        return proceedFlow {
            datasource.createTransaction(
                flightId,
                adult,
                child,
                baby,
                transactionRequest,
            )
        }
    }
}
