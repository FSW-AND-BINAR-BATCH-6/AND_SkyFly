package com.kom.skyfly.data.mapper

import com.kom.skyfly.data.model.transaction.paymentstatus.ItemsPaymentStatus
import com.kom.skyfly.data.model.transaction.paymentstatus.PaymentStatusModel
import com.kom.skyfly.data.model.transaction.paymentstatus.VaNumberModel
import com.kom.skyfly.data.source.network.model.paymentstatus.ItemsPaymentStatusResponse
import com.kom.skyfly.data.source.network.model.paymentstatus.PaymentStatusResponse
import com.kom.skyfly.data.source.network.model.paymentstatus.VaNumber

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
fun PaymentStatusResponse?.toPaymentStatus(): PaymentStatusModel =
    this?.let {
        PaymentStatusModel(
            data = it.data.toItemsPaymentStatusDomain(),
            message = it.message.orEmpty(),
            status = it.status ?: false,
        )
    } ?: PaymentStatusModel(null, "", false)

fun ItemsPaymentStatusResponse?.toItemsPaymentStatusDomain(): ItemsPaymentStatus? =
    this?.let {
        ItemsPaymentStatus(
            currency = it.currency.orEmpty(),
            expiryTime = it.expiryTime.orEmpty(),
            grossAmount = it.grossAmount.orEmpty(),
            merchantId = it.merchantId.orEmpty(),
            orderId = it.orderId.orEmpty(),
            paymentStatus = it.paymentStatus.orEmpty(),
            paymentType = it.paymentType.orEmpty(),
            signatureKey = it.signatureKey.orEmpty(),
            transactionId = it.transactionId.orEmpty(),
            transactionStatus = it.transactionStatus.orEmpty(),
            transactionTime = it.transactionTime.orEmpty(),
            vaNumbers = it.vaNumbers.toVaNumberDomainList(),
        )
    }

fun List<VaNumber?>?.toVaNumberDomainList(): List<VaNumberModel> = this?.mapNotNull { it.toVaNumberDomain() } ?: emptyList()

fun VaNumber?.toVaNumberDomain(): VaNumberModel? =
    this?.let {
        VaNumberModel(
            bank = it.bank.orEmpty(),
            vaNumber = it.vaNumber.orEmpty(),
        )
    }
