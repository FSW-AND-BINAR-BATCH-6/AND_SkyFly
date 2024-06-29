package com.kom.skyfly.data.repository.searchhistory

import app.cash.turbine.test
import com.kom.skyfly.data.datasource.searchhistory.SearchHistoryDataSource
import com.kom.skyfly.data.model.searchhistory.SearchHistory
import com.kom.skyfly.data.source.local.database.entity.SearchHistoryEntity
import com.kom.skyfly.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Written by Komang Yuda Saputra
 * Github : https://github.com/YudaSaputraa
 */
class SearchHistoryRepositoryImplTest {
    @MockK
    lateinit var datasource: SearchHistoryDataSource
    private lateinit var repository: SearchHistoryRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = SearchHistoryRepositoryImpl(datasource)
    }

    @Test
    fun createSearchHistory_success() {
        val mockProduct = mockk<SearchHistory>(relaxed = true)
        every { mockProduct.id } returns 1
        coEvery { datasource.insertSearchHistory(any()) } returns 1
        runTest {
            repository.createSearchHistory("feoihfieo", "JKT-DPS")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(2201)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Success)
                    assertEquals(true, actualData.payload)
                    coVerify { datasource.insertSearchHistory(any()) }
                }
        }
    }

    @Test
    fun createSearchHistory_loading() {
        val mockProduct = mockk<SearchHistory>(relaxed = true)
        every { mockProduct.id } returns 1
        coEvery { datasource.insertSearchHistory(any()) } returns 1
        runTest {
            repository.createSearchHistory("feoihfieo", "JKT-DPS")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(111)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Loading)
                    coVerify { datasource.insertSearchHistory(any()) }
                }
        }
    }

    @Test
    fun createSearchHistory_error() {
        val mockSearchHistory = mockk<SearchHistory>(relaxed = true)
        every { mockSearchHistory.id } returns 1
        coEvery { datasource.insertSearchHistory(any()) } throws IllegalStateException("error")
        runTest {
            repository.createSearchHistory("feoihfieo", "JKT-DPS")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(2201)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Error)
                    coVerify { datasource.insertSearchHistory(any()) }
                }
        }
    }

    @Test
    fun deleteSearchHistory() {
        val mockSearch =
            SearchHistory(
                id = 1,
                userId = "geojegegf",
                searchHistory = "JKT-DPS",
            )
        coEvery { datasource.deleteSearchHistory(any()) } returns 1
        runTest {
            val result =
                repository.deleteSearchHistory(mockSearch).map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val actualResult = expectMostRecentItem()
                    assertTrue(actualResult is ResultWrapper.Success)
                }
            coVerify { datasource.deleteSearchHistory(any()) }
        }
    }

    @Test
    fun deleteAllSearchHistory() {
        coEvery { datasource.deleteAllSearchHistory() } returns Unit
        runTest {
            val result =
                repository.deleteAllSearchHistory().map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val actualResult = expectMostRecentItem()
                    assertTrue(actualResult is ResultWrapper.Success)
                }
            coVerify { datasource.deleteAllSearchHistory() }
        }
    }

    @Test
    fun getUserSearchHistory_success() {
        val entity1 =
            SearchHistoryEntity(
                id = 1,
                userId = "ejiegef",
                searchHistory = "JKT",
            )
        val entity2 =
            SearchHistoryEntity(
                id = 2,
                userId = "ejiegef",
                searchHistory = "DPS",
            )
        val mockList = listOf(entity1, entity2)
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { datasource.getAllSearchHistory() } returns mockFlow
        runTest {
            repository.getUserSearchHistory().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockList.size, actualData.payload?.size)
                verify { datasource.getAllSearchHistory() }
            }
        }
    }

    @Test
    fun getUserSearchHistory_error() {
        every { datasource.getAllSearchHistory() } returns
            flow {
                throw IllegalStateException("error")
            }
        runTest {
            repository.getUserSearchHistory().map {
                delay(100)
                it
            }.test {
                delay(2210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                verify { datasource.getAllSearchHistory() }
            }
        }
    }

//    @Test
//    fun getUserSearchHistory_empty() {
//        val mockList = listOf<SearchHistoryEntity>()
//        val mockFlow = flow { emit(mockList) }
//        every { datasource.getAllSearchHistory() } returns mockFlow
//
//        runTest {
//            repository.getUserSearchHistory().map {
//                delay(100)
//                it
//            }.test {
//                delay(210)
//                val actualResult = expectMostRecentItem()
//                assertTrue(actualResult is ResultWrapper.Empty)
//                verify { datasource.getAllSearchHistory() }
//            }
//        }
//    }

    @Test
    fun getUserSearchHistory_loading() {
        val entity1 =
            SearchHistoryEntity(
                id = 1,
                userId = "ejiegef",
                searchHistory = "JKT",
            )
        val entity2 =
            SearchHistoryEntity(
                id = 2,
                userId = "ejiegef",
                searchHistory = "DPS",
            )
        val mockList = listOf(entity1, entity2)
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { datasource.getAllSearchHistory() } returns mockFlow
        runTest {
            repository.getUserSearchHistory().map {
                delay(100)
                it
            }.test {
                delay(111)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                verify { datasource.getAllSearchHistory() }
            }
        }
    }
}
