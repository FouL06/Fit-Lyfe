package com.example.lifeapp

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DatabaseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: User) : Long

    @Query("select * from user_table where userID = :key")
    fun getUser(key: Long): MutableLiveData<User?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Weather): Long

    @Query("select * from weather_table where weatherID = :key")
    fun getWeather(key: Long): Weather?
}