package com.kom.skyfly.data.source.local.pref

import android.content.SharedPreferences
import com.kom.skyfly.utils.SharedPreferenceUtils.set

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface UserPreference {
    fun isOnBoardingShow(): Boolean

    fun setOnBoardingShow(isShow: Boolean)

    fun saveUserToken(token: String?)

    fun getUserToken(): String?
}

class UserPreferenceImpl(private val pref: SharedPreferences) : UserPreference {
    override fun isOnBoardingShow(): Boolean = pref.getBoolean(KEY_ON_BOARDING_SHOW, false)

    override fun setOnBoardingShow(isShow: Boolean) {
        pref[KEY_ON_BOARDING_SHOW] = isShow
    }

    override fun saveUserToken(token: String?) {
        pref[KEY_TOKEN] = token
    }

    override fun getUserToken(): String? {
        return pref.getString(KEY_TOKEN, null)
    }

    companion object {
        const val PREF_NAME = "skyfly-pref"
        const val KEY_ON_BOARDING_SHOW = "KEY_ON_BOARDING_SHOW"
        const val KEY_TOKEN = "KEY_TOKEN"
    }
}
