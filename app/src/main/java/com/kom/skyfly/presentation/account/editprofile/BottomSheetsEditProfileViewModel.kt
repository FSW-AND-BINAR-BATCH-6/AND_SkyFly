package com.kom.skyfly.presentation.account.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.profiles.ProfileRepository
import kotlinx.coroutines.Dispatchers

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class BottomSheetsEditProfileViewModel(private val profileRepository: ProfileRepository) :
    ViewModel() {
    fun updateProfile(
        id: String,
        name: String,
        phoneNumber: String,
    ) = profileRepository
        .updateProfile(id, name, phoneNumber)
        .asLiveData(Dispatchers.IO)
}
