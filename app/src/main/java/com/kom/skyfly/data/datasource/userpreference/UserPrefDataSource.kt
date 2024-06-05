package com.kom.skyfly.data.datasource.userpreference

import com.kom.skyfly.data.source.local.pref.UserPreference

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface UserPrefDataSource {
    fun isOnBoardingShow(): Boolean

    fun setOnBoardingShow(isShow: Boolean)

    fun saveUserToken(token: String?)

    fun getUserToken(): String?
}

class UserPrefDataSourceImpl(private val userPreference: UserPreference) : UserPrefDataSource {
    override fun isOnBoardingShow(): Boolean {
        return userPreference.isOnBoardingShow()
    }

    override fun setOnBoardingShow(isShow: Boolean) {
        return userPreference.setOnBoardingShow(isShow)
    }

    override fun saveUserToken(token: String?) {
        return userPreference.saveUserToken(token)
    }

    override fun getUserToken(): String? {
        return userPreference.getUserToken()
    }
}
