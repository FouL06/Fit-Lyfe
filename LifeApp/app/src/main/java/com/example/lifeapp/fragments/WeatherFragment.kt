package com.example.lifeapp.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import com.example.lifeapp.MainActivity
import com.example.lifeapp.R
import com.example.lifeapp.databinding.FragmentProfileBinding
import com.example.lifeapp.userManager
import com.example.lifeapp.viewmodels.UserViewModel

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text
import java.net.URL
import java.net.URLEncoder

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class WeatherFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // Global Variables
    private lateinit var tvCity: TextView
    private lateinit var tvWeather: TextView
    private lateinit var tvDegree: TextView
    private var currentCountry: String? = null
    private var currentCity: String? = null
    private var syncedLocation: String? = null
    private var savedLocation: String? = null
    private var savedTemp: String? = null
    private var savedWeather: String? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var API: String? = null
    private val viewModel: UserViewModel by viewModels()

    private val userViewModel: UserViewModel by activityViewModels()
    private var binding: FragmentProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        API = ""

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireContext())
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

//        if (savedInstanceState != null) {
//            tvCity!!.text = savedInstanceState.getString("location")
//            tvDegree!!.text = savedInstanceState.getString("temperature")
//            tvWeather!!.text = savedInstanceState.getString("weather")
//        }
    }

    inner class weatherUpdater() : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg p0: String?): String? {
            var response: String?
            try {
                response =
                    URL("https://api.openweathermap.org/geo/1.0/direct?q=$syncedLocation&limit=1&appid=$API").readText(
                        Charsets.UTF_8
                    )
                val jsonObj = JSONArray(response).getJSONObject(0)
                val lat = jsonObj.getDouble("lat")
                val lon = jsonObj.getDouble("lon")

                //dataManager.setUserLat(lat)
                //dataManager.setUserLong(lon)



                response =
                    URL("https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&units=metric&appid=$API").readText(
                        Charsets.UTF_8
                    )
            } catch (e: Exception) {
                response = null
                print(e)
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
               val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
               val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val temp = main.getString("temp") + "°C"
                val address = jsonObj.getString("name") + ", " + sys.getString("country")
                tvCity.text = address
                tvWeather.text = weather.getString("description")
                userViewModel.setWeatherDescription(tvWeather.text.toString())
                tvDegree.text = temp
                userViewModel.setWeatherTemperature(temp)


            } catch (e: Exception) {
                println(e)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
       // dataManager = context as MainActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val inf: View = inflater.inflate(R.layout.fragment_weather, container, false)
        val syncLocation = inf.findViewById<Button>(R.id.syncLocationButton)

        tvCity = inf.findViewById<TextView>(R.id.tv_city)
        tvWeather = inf.findViewById(R.id.tv_weather)
        tvDegree = inf.findViewById(R.id.tv_degree)


        tvCity.text = userViewModel.getUserCity()
//dataManager.getUserCity()
        tvWeather.text = userViewModel.getWeatherDescription()
//dataManager.getWeatherDescription()
        tvDegree.text = userViewModel.getWeatherTemperature()
//dataManager.getWeatherTemperature()


        syncLocation.setOnClickListener() {
            getLastKnownLocation()
            weatherUpdater().execute()
        }
        return inf
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            // Set up the button click listeners
            viewModel = userViewModel
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        savedLocation = tvCity!!.text.toString()
        savedTemp = tvDegree!!.text.toString()
        savedWeather =  tvWeather!!.text.toString()

        outState.putString("location", savedLocation)
        outState.putString("temperature", savedTemp)
        outState.putString("weather", savedWeather)
    }

    private fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1000)
            return
        }
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener {
                //dataManager.getUserCountry()
                this.currentCountry = userViewModel.getUserCountry()
                this.currentCity = userViewModel.getUserCity()

                this.syncedLocation = URLEncoder.encode(currentCity!! + "," + currentCountry!!, "utf-8")
//            }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1000 -> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastKnownLocation()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
