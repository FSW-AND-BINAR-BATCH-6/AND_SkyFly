package com.kom.skyfly.data.model.profiles

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
@Parcelize
data class Profiles(
    val id: String? = UUID.randomUUID().toString(),
    val userId: String?,
    val email: String?,
    val fullName: String?,
    val phoneNumber: String?,
) : Parcelable
