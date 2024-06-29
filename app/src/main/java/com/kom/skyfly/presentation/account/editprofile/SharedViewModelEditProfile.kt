package com.kom.skyfly.presentation.account.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.skyfly.data.repository.profiles.ProfileRepository
import kotlinx.coroutines.Dispatchers

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class SharedViewModelEditProfile(private val profileRepository: ProfileRepository) :
    ViewModel() {
    fun updateProfile(
        name: String?,
        phoneNumber: String?,
        familyName: String?,
        password: String?,
        confirmPassword: String?,
    ) = profileRepository
        .updateProfile(name, phoneNumber, familyName, password, confirmPassword)
        .asLiveData(Dispatchers.IO)
}
