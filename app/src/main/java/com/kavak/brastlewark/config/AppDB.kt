package com.kavak.brastlewark.config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kavak.brastlewark.constans.Constants
import com.kavak.brastlewark.data.Converters
import com.kavak.brastlewark.data.dao.CitizenDAO
import com.kavak.brastlewark.data.entities.Citizen

@Database(
    entities = [Citizen::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDB : RoomDatabase() {
    abstract fun citizenDAO(): CitizenDAO

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDB? = null

        fun getInstance(context: Context): AppDB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDB {
            return Room.databaseBuilder(context, AppDB::class.java, Constants.DB_NAME)
                .build()
        }

    }
}