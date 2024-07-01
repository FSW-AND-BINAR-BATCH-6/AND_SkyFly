package com.kom.skyfly.data.repository.tickets

import com.kom.skyfly.data.datasource.tickets.TicketsDataSource
import com.kom.skyfly.data.mapper.toTicketsModel
import com.kom.skyfly.data.model.tickets.TicketsModel
import com.kom.skyfly.utils.ResultWrapper
import com.kom.skyfly.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface TicketsRepository {
    fun getTicketsById(id: String): Flow<ResultWrapper<TicketsModel>>
}

class TicketsRepositoryImpl(private val dataSource: TicketsDataSource) : TicketsRepository {
    override fun getTicketsById(id: String): Flow<ResultWrapper<TicketsModel>> {
        return proceedFlow { dataSource.getTicketById(id).toTicketsModel() }
    }
}
