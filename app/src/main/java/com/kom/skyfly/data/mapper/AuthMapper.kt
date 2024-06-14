package com.kom.skyfly.data.mapper

import com.kom.skyfly.data.model.auth.Auth
import com.kom.skyfly.data.source.network.model.userprofile.UserProfileResponse

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
fun UserProfileResponse?.toUserIsLoggedIn() =
    Auth(
        status = this?.status.toString(),
    )

fun Collection<UserProfileResponse>?.toUsersLoggedIn() = this?.map { it.toUserIsLoggedIn() } ?: listOf()
