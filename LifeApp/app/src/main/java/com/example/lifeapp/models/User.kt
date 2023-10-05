package com.example.lifeapp.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User (
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
    var city: String,
    @ColumnInfo(name = "country")
    var country: String,
    @ColumnInfo(name = "imgPath")
    var imgPath: String,
    @ColumnInfo(name = "weatherDescription")
    var weatherDescription: String,
    @ColumnInfo(name = "weatherTemp")
    var weatherTemp: String
    )