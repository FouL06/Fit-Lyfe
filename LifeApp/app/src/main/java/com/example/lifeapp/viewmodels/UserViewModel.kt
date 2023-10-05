package com.example.lifeapp.viewmodels


import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import  com.example.lifeapp.models.User
import  com.example.lifeapp.repositories.UserRepository


class UserViewModel(application: Application) : AndroidViewModel(application){

    @SuppressLint("StaticFieldLeak")
    private var appContext = application.applicationContext
    private var userProfileRepository = UserRepository.getInstance(appContext)
    var currUser = MutableLiveData<User?>()

    init{
        getUser()
    }

    private fun getUser() {
        var user : User? = userProfileRepository.userDao.getUser(1)
        if(user == null) {
            // give db defualt user
            user = User(
                userID = 0,
                firstName = "",
                lastName = "",
                weight = 25,
                height=0,
                age=25,
                activityLevel = "0",
                sex="Other",
                bmr = 0.0,
                city="",
                country="",
                imgPath = "",
                weatherDescription = "",
                weatherTemp = ""
            )
            userProfileRepository.insertUser(user)
        }
        currUser.value = user

    }

    fun setUserHeight(h: Int) {
        currUser.value?.height = h
        currUser.value?.let { userProfileRepository.updateUser(it) }
    }

    fun getUserHeight(): Int {
        return currUser.value?.height ?: 0
    }

    fun setUserWeight(w: Int) {
        currUser.value?.weight = w
        currUser.value?.let { userProfileRepository.updateUser(it) }
    }

    fun getUserWeight(): Int {
        return currUser.value?.weight ?: 25
    }

    fun setUserAge(a: Int) {
        currUser.value?.age = a
        currUser.value?.let { userProfileRepository.updateUser(it) }
    }

    fun getUserAge(): Int {
        return currUser.value?.age ?: 25
    }

    fun setUserSex(s: String) {
        currUser.value?.sex = s
        currUser.value?.let { userProfileRepository.updateUser(it) }
    }

    fun getUserSex(): String {
        return currUser.value?.sex ?: "Other"
    }

    fun setUserActivityLevel(a: Int) {
        currUser.value?.activityLevel = a.toString()
        currUser.value?.let { userProfileRepository.updateUser(it) }
    }

    fun getUserActivityLevel(): String {
        return currUser.value?.activityLevel ?: "0"
    }

    fun setUserFirstName(n: String) {
        currUser.value?.firstName = n
        currUser.value?.let { userProfileRepository.updateUser(it) }
    }

    fun getUserFirstName(): String {
        return currUser.value?.firstName ?: ""
    }

    fun setUserLastName(n: String) {
        currUser.value?.lastName = n
        currUser.value?.let { userProfileRepository.updateUser(it) }
    }

    fun getUserLastName(): String {
        return currUser.value?.lastName ?: ""
    }

    fun setUserCity(c: String) {
        currUser.value?.city = c
        currUser.value?.let { userProfileRepository.updateUser(it) }
    }

    fun getUserCity(): String {
        return currUser.value?.city ?: ""
    }




    fun setUserCountry(c: String) {
        currUser.value?.country = c
        currUser.value?.let { userProfileRepository.updateUser(it) }
    }

    fun getUserCountry(): String {
        return currUser.value?.country ?: ""
    }

    fun setUserBMR(d: Double) {
        currUser.value?.bmr = d
        currUser.value?.let { userProfileRepository.updateUser(it) }
    }

    fun getUserBMR(): Double {
        return currUser.value?.bmr ?: 0.0
    }

    fun setIMGPath(c: String) {
        currUser.value?.imgPath = c
        currUser.value?.let { userProfileRepository.updateUser(it) }
    }

    fun getIMGPath(): String {
        return currUser.value?.imgPath ?: ""
    }

    fun getWeatherDescription(): String {
        return currUser.value?.weatherDescription ?: ""
    }

    fun getWeatherTemperature(): String {
        return currUser.value?.weatherTemp ?: ""
    }

    fun setWeatherDescription(toString: String) {
        currUser.value?.weatherDescription = toString
        currUser.value?.let { userProfileRepository.updateUser(it) }
    }

    fun setWeatherTemperature(temp: String) {
        currUser.value?.weatherTemp = temp
        currUser.value?.let { userProfileRepository.updateUser(it) }
    }


}