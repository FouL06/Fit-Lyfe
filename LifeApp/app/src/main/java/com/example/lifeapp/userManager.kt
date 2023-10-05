package com.example.lifeapp

interface userManager {

    fun setUserCity(c: String)
    fun getUserCity(): String
    fun setUserLat(c:Double)
    fun setUserLong(c:Double)
    fun getUserLat(): Double
    fun getUserLong(): Double
    fun setUserCountry(c: String)
    fun getUserCountry(): String

    fun setWeatherDescription(s : String)
    fun getWeatherDescription() : String

    fun setWeatherTemperature(s: String)
    fun getWeatherTemperature() : String


}