package com.kom.skyfly.data.repository.home.destination_favourite

import com.kom.skyfly.data.datasource.home.HomeDataSource
import com.kom.skyfly.data.mapper.home.toDestinationFavourites
import com.kom.skyfly.data.model.home.destination_favourite.DestinationFavourite
import com.kom.skyfly.utils.ResultWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface DestinationFavouriteRepository {
    fun getAllDestinationFavourite(): Flow<ResultWrapper<List<DestinationFavourite>>>
}

class DestinationFavouriteRepositoryImpl(private val destinationFavouriteDataSource: HomeDataSource) :
    DestinationFavouriteRepository {
    override fun getAllDestinationFavourite(): Flow<ResultWrapper<List<DestinationFavourite>>> {
        return flow {
            emit(ResultWrapper.Loading())
            delay(1000)
            val result =
                destinationFavouriteDataSource.getDestinationFavourites().data.toDestinationFavourites()
            emit(ResultWrapper.Success(result))
        }
    }
}
