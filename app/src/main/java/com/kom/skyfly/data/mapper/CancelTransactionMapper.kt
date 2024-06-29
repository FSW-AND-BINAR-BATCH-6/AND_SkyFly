package com.kom.skyfly.data.mapper

import com.kom.skyfly.data.model.transaction.cancel.ItemsCancelTransactionModel
import com.kom.skyfly.data.source.network.model.transaction.cancel.ItemsCancelTransactionResponse

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
fun ItemsCancelTransactionResponse?.toCancelTransaction() =
    ItemsCancelTransactionModel(
        currency = this?.currency.orEmpty(),
        grossAmount = this?.grossAmount.orEmpty(),
        merchantId = this?.merchantId.orEmpty(),
        orderId = this?.orderId.orEmpty(),
        paymentStatus = this?.paymentStatus.orEmpty(),
        paymentType = this?.paymentType.orEmpty(),
        transactionId = this?.transactionId.orEmpty(),
        transactionStatus = this?.transactionStatus.orEmpty(),
        transactionTime = this?.transactionTime.orEmpty(),
    )
