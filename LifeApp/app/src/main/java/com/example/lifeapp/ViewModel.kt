package com.example.lifeapp

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class UserViewModel(application: Application) : AndroidViewModel(application), userManager {

    private var height : Int = 0
    private var hPro : Int = 15
    private var weight : Int = 155
    private var age : Int = 25
    private var activityLevel : String = "0"
    private var sex : String = "Other"
    private var firstName : String = ""
    private var lastName : String = ""
    private var city : String = ""
    private var country : String = ""
    private var bmr : Double = 0.0
    private var lat : Double = 0.0
    private var lon : Double = 0.0

    private var profilePicturePath : String = ""

    private var weatherDescription : String = "Weather"
    private var weatherTemperature : String = "26 \u2109"

    private lateinit var image : ImageView


    private val context = getApplication<Application>().applicationContext

    private var db : lifeAppDatabase = lifeAppDatabase.getInstance(context)

    private var user : MutableLiveData<User?> = db.databaseDAO.getUser(0)

    override fun setUserHeight(h: Int) {
        user.value?.height = h
    }

    override fun getUserHeight(): Int {
        return user.value?.height ?: height
    }

    override fun setUserHeightPro(h: Int) {
        hPro = h
    }

    override fun getUserHeightPro() :Int {
        return hPro
    }


    override fun setUserWeight(w: Int) {
        user.value?.weight = w
    }

    override fun getUserWeight(): Int {
        return user.value?.weight ?: weight
    }

    override fun setUserAge(a: Int) {
        user.value?.age = a
    }

    override fun getUserAge(): Int {
        return user.value?.age ?: age
    }

    override fun setUserSex(s: String) {
        user.value?.sex = s
    }

    override fun getUserSex() : String{
        return user.value?.sex ?: sex
    }


    override fun setUserActivityLevel(a: Int) {
        user.value?.activityLevel = a.toString()
    }

    override fun getUserActivityLevel() : String {
        return user.value?.activityLevel ?: activityLevel
    }

    override fun setUserFirstName(n: String) {
        user.value?.firstName = n
    }

    override fun getUserFirstName() : String {
        return user.value?.firstName ?: firstName
    }

    override fun setUserLastName(n: String) {
        user.value?.lastName = n
    }

    override fun setUserLong(c: Double) {
        lon = c
    }

    override fun getUserLat(): Double {
        return lat
    }

    override fun getUserLong(): Double {
        return lon
    }

    override fun getUserLastName() : String {
        return user.value?.lastName ?: lastName
    }
    override fun setUserCity(c: String) {
        city = c
    }
    override fun getUserCity(): String {
        return city
    }

    override fun setUserLat(c: Double) {
        lat = c
    }

    override fun setUserCountry(c: String) {
        country = c
    }
    override fun getUserCountry(): String {
        return country
    }

    override fun setUserBMR(d: Double) {
        user.value?.bmr = d
    }

    override fun getUserBMR(): Double {
        return user.value?.bmr ?: bmr
    }


    override fun setWeatherDescription(s: String) {
        weatherDescription = s
    }

    override fun getWeatherDescription(): String {
        return weatherDescription
    }

    override fun setWeatherTemperature(s: String) {
        weatherTemperature = s
    }

    override fun getWeatherTemperature(): String {
        return weatherTemperature
    }

    override fun setProfilePicPath(s: String) {
        user.value?.imgPath = s
    }

    override fun getProfilePicPath(): String {
        return user.value?.imgPath ?: profilePicturePath
    }

    override fun setPic() {
        image.setImageBitmap(BitmapFactory.decodeFile(profilePicturePath))
    }


    override fun onCleared() {
        super.onCleared()
        if(user.value != null){
            db.databaseDAO.insert(user.value!!)
        }
        else{
        }
        //TODO cancel coroutine scope
    }

}
