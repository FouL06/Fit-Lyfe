package com.example.lifeapp

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lifeapp.fragments.*
import com.example.lifeapp.viewmodels.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private var currentScreen : String = ""
    private var profilePicturePath : String = ""

    private var weatherDescription : String = "Weather"
    private var weatherTemperature : String = "26 \u2109"

    private lateinit var image : ImageView

//    private val viewModel : UserViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        loadSavedData()


        // Fragments
        val bmiFragment = BmiFragment()
        val caloriesFragment = CaloriesFragment()
        val homeFragment = HomeFragment()
        val weatherFragment = WeatherFragment()
        val profileFragment = ProfileFragment()

        image = findViewById<ImageView>(R.id.main_pic)

//        if(profilePicturePath != "")
//            setPic()

        if(savedInstanceState != null)
        {
            loadPreviousInstance(savedInstanceState)
            when(currentScreen){
                "home" -> makeCurrentFragment(homeFragment)
                "bmi" -> makeCurrentFragment(bmiFragment)
                "calories" -> makeCurrentFragment(caloriesFragment)
                "hike" -> makeCurrentFragment(homeFragment)
                "weather" -> makeCurrentFragment(weatherFragment)
                "profile" -> makeCurrentFragment(profileFragment)
            }
        }
        else
            makeCurrentFragment(homeFragment)

        val navBottom = findViewById<BottomNavigationView>(R.id.nav_bottom)
        navBottom.selectedItemId = R.id.ic_hike
        navBottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_bmi -> {
                    makeCurrentFragment(bmiFragment)
                    currentScreen = "bmi"
                }
                R.id.ic_calories -> {
                    makeCurrentFragment(caloriesFragment)
                    currentScreen = "calories"
                }
                R.id.ic_hike -> {
                    makeCurrentFragment(homeFragment)
                    currentScreen = "home"
                }
                R.id.ic_weather -> {
                    makeCurrentFragment(weatherFragment)
                    currentScreen = "weather"
                }
                R.id.pic_profile -> {
                    makeCurrentFragment(profileFragment)
                    currentScreen = "profile"
                }
            }
            true
        }
    }



    override fun onSaveInstanceState(outState: Bundle){
        super.onSaveInstanceState(outState)
        outState.putString("currScreen", currentScreen)
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }


    override fun onStop() {
        super.onStop()
        val sharedPref = this@MainActivity.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("currScreen", currentScreen)
            apply()
        }

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

}