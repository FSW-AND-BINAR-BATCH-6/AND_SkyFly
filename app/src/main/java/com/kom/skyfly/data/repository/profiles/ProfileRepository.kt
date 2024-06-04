package com.kom.skyfly.data.repository.profiles

import com.kom.skyfly.data.datasource.profiles.ProfileDataSource
import com.kom.skyfly.data.model.profiles.Profiles
import com.kom.skyfly.utils.ResultWrapper
import com.kom.skyfly.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface ProfileRepository {
    fun getProfile(): Flow<ResultWrapper<Profiles>>
}

class ProfileRepositoryImpl(private val dataSource: ProfileDataSource) : ProfileRepository {
    override fun getProfile(): Flow<ResultWrapper<Profiles>> {
        return proceedFlow { dataSource.getProfilesData() }
    }
}
