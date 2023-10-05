package com.example.lifeapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lifeapp.models.Weather

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Weather): Long

    @Query("select * from weather_table where weatherID = :key")
    fun getWeather(key: Long): Weather?
}