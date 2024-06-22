package com.kom.skyfly.presentation.checkout.bookersbiodata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.profiles.ProfileRepository
import kotlinx.coroutines.Dispatchers

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class BookersBiodataViewModel(private val profileRepository: ProfileRepository) : ViewModel() {
    fun getProfile() =
        profileRepository
            .getProfile()
            .asLiveData(Dispatchers.IO)
}
