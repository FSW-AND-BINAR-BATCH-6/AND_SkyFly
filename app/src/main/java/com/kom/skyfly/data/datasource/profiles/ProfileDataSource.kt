package com.kom.skyfly.data.datasource.profiles

import com.kom.skyfly.data.model.profiles.Profiles

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface ProfileDataSource {
    fun getProfilesData(): Profiles
}

class ProfileDataSourceImpl() : ProfileDataSource {
    override fun getProfilesData(): Profiles {
        return Profiles(
            email = "komangyudasaputra06@gmail.com",
            fullName = "Komang Yuda Saputra",
            phoneNumber = "0988766677467",
        )
    }
}
