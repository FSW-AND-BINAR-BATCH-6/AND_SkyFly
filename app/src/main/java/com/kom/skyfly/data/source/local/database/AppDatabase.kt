package com.kom.skyfly.data.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kom.skyfly.data.source.local.database.dao.SearchHistoryDao
import com.kom.skyfly.data.source.local.database.entity.SearchHistoryEntity

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
@Database(
    entities = [SearchHistoryEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        private const val DB_NAME = "skyfly.db"

        fun createInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME,
            ).fallbackToDestructiveMigration().build()
        }
    }
}
