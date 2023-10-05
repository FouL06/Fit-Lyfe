package com.example.lifeapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Weather::class], version = 1, exportSchema = false)
abstract class lifeAppDatabase: RoomDatabase() {
    abstract val databaseDAO: DatabaseDAO
    companion object {
        @Volatile
        private var INSTANCE: lifeAppDatabase? = null

        fun getInstance(context: Context): lifeAppDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        lifeAppDatabase::class.java,
                        "lifeapp_database"
                    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}