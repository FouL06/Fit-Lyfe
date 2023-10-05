package com.example.lifeapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userID: Long = 0,
    @ColumnInfo(name = "firstName")
    var firstName: String,
    @ColumnInfo(name = "lastName")
    var lastName: String,
    @ColumnInfo(name = "height")
    var height: Int,
    @ColumnInfo(name = "weight")
    var weight: Int,
    @ColumnInfo(name = "bmr")
    var bmr: Double,
    @ColumnInfo(name = "age")
    var age: Int,
    @ColumnInfo(name = "sex")
    var sex: String,
    @ColumnInfo(name = "activity level")
    var activityLevel: String,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "imgPath")
    var imgPath: String,
)

@Entity(tableName = "weather_table")
data class Weather(
    @PrimaryKey(autoGenerate = true)
    val weatherID: Long = 0,
    @ColumnInfo(name = "lat")
    val lat: Double,
    @ColumnInfo(name = "long")
    val long: Double,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "temp")
    val temp: String,
    @ColumnInfo(name = "description")
    val description: String,
)