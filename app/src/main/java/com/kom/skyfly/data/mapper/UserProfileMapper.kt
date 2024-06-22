package com.kom.skyfly.data.mapper

import com.kom.skyfly.data.model.profiles.Profiles
import com.kom.skyfly.data.source.network.model.userprofile.UserProfileResponse

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
fun UserProfileResponse?.toUserProfile() =
    Profiles(
        email = this?.data?.auth?.email.orEmpty(),
        fullName = this?.data?.name.orEmpty(),
        familyName = this?.data?.familyName.orEmpty(),
        phoneNumber = this?.data?.phoneNumber,
        userId = this?.data?.id,
    )

fun Collection<UserProfileResponse>?.toProfiles() = this?.map { it.toUserProfile() } ?: listOf()
