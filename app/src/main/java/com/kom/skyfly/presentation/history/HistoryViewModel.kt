package com.kom.skyfly.presentation.history

import androidx.lifecycle.ViewModel
import com.kom.skyfly.data.datasource.history.HistoryDataSource
import com.kom.skyfly.data.datasource.history.HistoryDataSourceImpl

class HistoryViewModel : ViewModel() {
    private val dataSource: HistoryDataSource by lazy {
        HistoryDataSourceImpl()
    }

    fun getHistoryData() = dataSource.getHistoryData()
}
