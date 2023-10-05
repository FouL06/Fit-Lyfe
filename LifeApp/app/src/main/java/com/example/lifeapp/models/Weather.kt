package com.example.lifeapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class Weather(
    @PrimaryKey(autoGenerate = true)
    var weatherID: Long? = 0,
    @ColumnInfo(name = "lat")
    var lat: Double?  = 0.0,
    @ColumnInfo(name = "long")
    var long: Double? = 0.0,
    @ColumnInfo(name = "city")
    var city: String? = "",
    @ColumnInfo(name = "country")
    var country: String? = "",
    @ColumnInfo(name = "temp")
    var temp: String? = "",
    @ColumnInfo(name = "description")
    var description: String? = ""
)