package com.kom.skyfly.data.repository.destinationfavorite

import com.kom.skyfly.data.datasource.destinationfavorite.DestinationFavoriteDataSource
import com.kom.skyfly.data.model.destinationfavorite.DestinationFavorite
import com.kom.skyfly.utils.ResultWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface DestinationFavoriteRepository {
    fun getAllDestinationFavorite(): Flow<ResultWrapper<List<DestinationFavorite>>>
}

class DestinationFavoriteRepositoryImpl(private val destinationFavoriteDataSource: DestinationFavoriteDataSource) :
    DestinationFavoriteRepository {
    override fun getAllDestinationFavorite(): Flow<ResultWrapper<List<DestinationFavorite>>> {
        return flow {
            emit(ResultWrapper.Loading())
            delay(1000)
            val result = destinationFavoriteDataSource.getAllDestinationFavorite()
            emit(ResultWrapper.Success(result))
        }
    }
}
