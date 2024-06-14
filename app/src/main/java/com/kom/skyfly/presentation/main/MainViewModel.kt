package com.kom.skyfly.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kom.skyfly.data.model.home.search.Airport
import com.kom.skyfly.data.repository.userpref.UserPrefRepository

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class MainViewModel(
    private val userPrefRepository: UserPrefRepository,
) : ViewModel() {
    fun getUserToken() = userPrefRepository.getUserToken()

    // Set Destination
    var isStartDestination: Boolean? = null
    private val _sourceDestination = MutableLiveData<Airport?>()
    val sourceDestination: LiveData<Airport?> get() = _sourceDestination

    fun setDestination(value: Airport?) {
        _sourceDestination.value = value
    }

    // Set start time
    private val _startTime = MutableLiveData<String?>()
    val startTime: LiveData<String?> get() = _startTime

    fun setStartTime(value: String?) {
        _startTime.value = value
    }

    // Set return time
    private val _returnTime = MutableLiveData<String?>()
    val returnTime: LiveData<String?> get() = _returnTime

    fun setReturnTime(value: String?) {
        _returnTime.value = value
    }
}
