package com.kom.skyfly.data.repository.userpref

import com.kom.skyfly.data.datasource.userpreference.UserPrefDataSource

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface UserPrefRepository {
    fun isOnBoardingShow(): Boolean

    fun setOnBoardingShow(isShown: Boolean)

    fun saveUserToken(token: String?)

    fun getUserToken(): String?
}

class UserPrefRepositoryImpl(private val dataSource: UserPrefDataSource) : UserPrefRepository {
    override fun isOnBoardingShow(): Boolean {
        return dataSource.isOnBoardingShow()
    }

    override fun setOnBoardingShow(isShown: Boolean) {
        return dataSource.setOnBoardingShow(isShown)
    }

    override fun saveUserToken(token: String?) {
        return dataSource.saveUserToken(token)
    }

    override fun getUserToken(): String? {
        return dataSource.getUserToken()
    }
}
