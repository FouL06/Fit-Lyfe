package com.example.lifeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lifeapp.models.User
import com.example.lifeapp.models.Weather


@Database(entities = [User::class, Weather::class], version = 1)
abstract class LifeAppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: LifeAppDatabase? = null

        fun getInstance(context: Context): LifeAppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): LifeAppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LifeAppDatabase::class.java,
                        "lifeapp_database"
                    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}