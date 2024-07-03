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

    // Set passenger total
    val passengerAdultCountLiveData =
        MutableLiveData(1).apply {
            postValue(1)
        }
    val passengerBabyCountLiveData =
        MutableLiveData(0).apply {
            postValue(0)
        }
    val passengerChildCountLiveData =
        MutableLiveData(0).apply {
            postValue(0)
        }
    val passengerCountLiveData =
        MutableLiveData(1).apply {
            postValue(1)
        }

    fun addTotal() {
        val totalCount =
            (passengerAdultCountLiveData.value ?: 1) +
                (passengerChildCountLiveData.value ?: 0) +
                (passengerBabyCountLiveData.value ?: 0)
        passengerCountLiveData.postValue(totalCount)
    }

    fun addAdult() {
        val adultCount = (passengerAdultCountLiveData.value ?: 1) + 1
        passengerAdultCountLiveData.postValue(adultCount)
    }

    fun addChild() {
        val childCount = (passengerChildCountLiveData.value ?: 0) + 1
        passengerChildCountLiveData.postValue(childCount)
    }

    fun addBaby() {
        val babyCount = (passengerBabyCountLiveData.value ?: 0) + 1
        passengerBabyCountLiveData.postValue(babyCount)
    }

    fun minusAdult() {
        if ((passengerAdultCountLiveData.value ?: 1) > 1) {
            val count = (passengerAdultCountLiveData.value ?: 1) - 1
            passengerAdultCountLiveData.postValue(count)
        }
    }

    fun minusChild() {
        if ((passengerChildCountLiveData.value ?: 0) > 0) {
            val count = (passengerChildCountLiveData.value ?: 0) - 1
            passengerChildCountLiveData.postValue(count)
        }
    }

    fun minusBaby() {
        if ((passengerBabyCountLiveData.value ?: 0) > 0) {
            val count = (passengerBabyCountLiveData.value ?: 0) - 1
            passengerBabyCountLiveData.postValue(count)
        }
    }

    // Set seatClassData
    private val _seatClass = MutableLiveData<String?>()
    val seatClass: LiveData<String?> get() = _seatClass

    fun setSeatClass(value: String?) {
        _seatClass.value = value
    }

    // Set roundTrip
    private val _roundTrip = MutableLiveData<Boolean?>(false)
    val roundTrip: LiveData<Boolean?> get() = _roundTrip

    fun setRoundTrip(value: Boolean?) {
        _roundTrip.value = value
    }
}
