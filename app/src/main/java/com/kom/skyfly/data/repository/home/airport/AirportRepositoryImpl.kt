package com.kom.skyfly.data.repository.home.airport

import com.kom.skyfly.data.datasource.home.HomeDataSource
import com.kom.skyfly.data.datasource.searchhistory.SearchHistoryDataSource
import com.kom.skyfly.data.mapper.home.toAirports
import com.kom.skyfly.data.mapper.toSearchDestinationHistoryEntity
import com.kom.skyfly.data.mapper.toSearchDestinationHistoryList
import com.kom.skyfly.data.model.home.search.Airport
import com.kom.skyfly.data.model.home.search_history.SearchDestinationHistory
import com.kom.skyfly.data.source.local.database.entity.SearchDestinationHistoryEntity
import com.kom.skyfly.utils.ResultWrapper
import com.kom.skyfly.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class AirportRepositoryImpl(
    private val dataSource: HomeDataSource,
    private val searchHistoryDataSource: SearchHistoryDataSource,
) : AirportRepository {
    override fun getAllAirportData(city: String?): Flow<ResultWrapper<List<Airport>>> {
        return flow {
            emit(ResultWrapper.Loading())
            val result = dataSource.getAllAirports(city = city).data.toAirports()
            emit(ResultWrapper.Success(result))
        }
    }

    override fun createSearchDestinationHistory(searchDestinationHistory: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            // Fetch all existing search history entries
            val existingHistory =
                searchHistoryDataSource.getAllSearchDestinationHistory()
                    .first() // Get the first emission from the Flow

            // Check if an entry with the same name already exists
            val entryExists = existingHistory.any { it.searchDestinationHistory == searchDestinationHistory }

            if (!entryExists) {
                val affectedRows =
                    searchHistoryDataSource.insertSearchDestinationHistory(
                        SearchDestinationHistoryEntity(
                            searchDestinationHistory = searchDestinationHistory,
                        ),
                    )
                affectedRows > 0
            } else {
                false // No new entry created
            }
        }
    }

    override fun deleteSearchDestinationHistory(item: SearchDestinationHistory): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { searchHistoryDataSource.deleteSearchDestinationHistory(item.toSearchDestinationHistoryEntity()) > 0 }
    }

    override fun deleteAllSearchDestinationHistory(): Flow<ResultWrapper<Unit>> {
        return proceedFlow { searchHistoryDataSource.deleteAllSearchDestinationHistory() }
    }

    override fun getUserSearchDestinationHistory(): Flow<ResultWrapper<List<SearchDestinationHistory>>> {
        return searchHistoryDataSource.getAllSearchDestinationHistory()
            .map { searchHistoryEntities ->
                val searchHistoryList = searchHistoryEntities.toSearchDestinationHistoryList()
                if (searchHistoryList != null) {
                    ResultWrapper.Success(searchHistoryList)
                } else {
                    ResultWrapper.Empty()
                }
            }.onStart {
                emit(ResultWrapper.Loading())
            }.catch { e ->
                emit(ResultWrapper.Error(Exception(e)))
            }
    }
}
