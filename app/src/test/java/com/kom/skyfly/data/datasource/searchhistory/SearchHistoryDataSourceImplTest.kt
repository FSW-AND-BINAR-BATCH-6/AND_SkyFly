package com.kom.skyfly.data.datasource.searchhistory

import app.cash.turbine.test
import com.kom.skyfly.data.source.local.database.dao.SearchHistoryDao
import com.kom.skyfly.data.source.local.database.entity.SearchHistoryEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Written by Komang Yuda Saputra
 * Github : https://github.com/YudaSaputraa
 */
class SearchHistoryDataSourceImplTest {
    @MockK
    lateinit var searchHistoryDao: SearchHistoryDao
    private lateinit var searchHistoryDataSource: SearchHistoryDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        searchHistoryDataSource = SearchHistoryDataSourceImpl(searchHistoryDao)
    }

    @Test
    fun getAllSearchHistory() {
        val entity1 = mockk<SearchHistoryEntity>()
        val entity2 = mockk<SearchHistoryEntity>()

        val listEntity = listOf(entity1, entity2)
        val mockFlow =
            flow {
                emit(listEntity)
            }
        every { searchHistoryDao.getAllSearchHistory() } returns mockFlow
        runTest {
            searchHistoryDataSource.getAllSearchHistory().test {
                val result = awaitItem()
                assertEquals(listEntity.size, result.size)
                awaitComplete()
            }
        }
    }

    @Test
    fun insertSearchHistory() {
        runTest {
            val mockEntity = mockk<SearchHistoryEntity>()
            coEvery { searchHistoryDao.insertSearchHistory(any()) } returns 1
            val result = searchHistoryDataSource.insertSearchHistory(mockEntity)
            coVerify { searchHistoryDao.insertSearchHistory(any()) }
            assertEquals(1, result)
        }
    }

    @Test
    fun updateSearchHistory() {
        runTest {
            val mockEntity = mockk<SearchHistoryEntity>()
            coEvery { searchHistoryDao.updateSearchHistory(any()) } returns 1
            val result = searchHistoryDataSource.updateSearchHistory(mockEntity)
            coVerify { searchHistoryDao.updateSearchHistory(any()) }
            assertEquals(1, result)
        }
    }

    @Test
    fun deleteSearchHistory() {
        runTest {
            val mockEntity = mockk<SearchHistoryEntity>()
            coEvery { searchHistoryDao.deleteSearchHistory(any()) } returns 1
            val result = searchHistoryDataSource.deleteSearchHistory(mockEntity)
            coVerify { searchHistoryDao.deleteSearchHistory(any()) }
            assertEquals(1, result)
        }
    }

    @Test
    fun deleteAllSearchHistory() {
        runTest {
            coEvery { searchHistoryDao.deleteAll() } returns Unit
            val result = searchHistoryDataSource.deleteAllSearchHistory()
            coVerify { searchHistoryDao.deleteAll() }
            assertEquals(Unit, result)
        }
    }
}
