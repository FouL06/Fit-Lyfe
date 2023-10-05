package com.example.lifeapp.fragments


import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.lifeapp.MainActivity
import com.example.lifeapp.R
import com.example.lifeapp.userManager
import com.example.lifeapp.viewmodels.UserViewModel

import java.io.File
import java.io.FileOutputStream
import java.util.*
import androidx.fragment.app.activityViewModels
import com.example.lifeapp.databinding.FragmentProfileBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class ProfileFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var imgProfileImage: Bitmap? = null
    private var imgThumbnail: ImageView? = null
    private var picBackup: String? = null
    private var userHeight : Int = 0
    private var userWeight : Int = 0
    private var userAge : Int = 0
    private var userActivityLevel : Int = 0
    private var userSex : Int = 0
    private var firstName : String = ""
    private var lastName : String = ""
    private var userCity : String = ""
    private var userCountry : String = ""
    private var imgPath : String = ""


    private lateinit var image : ImageView


    private val userViewModel: UserViewModel by activityViewModels()
    private var binding: FragmentProfileBinding? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val inf: View = inflater.inflate(R.layout.fragment_profile, container, false)
        registerSeekBars(inf)
        registerTextInputs(inf)
        registerImgButton(inf)

        image = inf.findViewById(R.id.img_profileImage) as ImageView
        if(userViewModel.getIMGPath() != "")
            image.setImageBitmap(BitmapFactory.decodeFile(userViewModel.getIMGPath()))

        return inf
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            // Set up the button click listeners
            viewModel = userViewModel
        }
    }

    private fun registerImgButton(inf: View){
        val btnCamera = inf.findViewById<Button>(R.id.btn_uploadPicture)
        btnCamera.setOnClickListener{
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                cameraLauncher.launch(cameraIntent)
            } catch (ex: ActivityNotFoundException) {
                print("Error here")
            }
        }
    }
    private fun registerTextInputs(inf: View) {
        val editNamesBtn = inf.findViewById<ImageView>(R.id.img_Edit) as ImageView
        val firstNameText = inf.findViewById<EditText>(R.id.tv_firstName) as EditText
        val lastNameText = inf.findViewById<EditText>(R.id.tv_lastName) as EditText
        val city = inf.findViewById<EditText>(R.id.tv_City) as EditText
        val country = inf.findViewById<EditText>(R.id.tv_Country) as EditText

        if(userViewModel.getUserFirstName() != "")
            firstNameText.setText(userViewModel.getUserFirstName())

        if(userViewModel.getUserLastName() != "")
            lastNameText.setText(userViewModel.getUserLastName())

        if(userViewModel.getUserCity() != "")
            city.setText(userViewModel.getUserCity())

        if(userViewModel.getUserCountry() != "")
            country.setText(userViewModel.getUserCountry())



        firstNameText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                userViewModel.setUserFirstName(s.toString())

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        lastNameText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                userViewModel.setUserLastName(s.toString());

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        city.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                userCity = s.toString()
                userViewModel.setUserCity(userCity)

            }

        })

        country.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                userCountry = s.toString()
                userViewModel.setUserCountry(userCountry)

            }

        })
    }

    private fun registerSeekBars(inf: View) {
        val displayHeightVal = inf.findViewById<View>(R.id.tv_heightValue) as TextView
        val displayWeightVal = inf.findViewById<View>(R.id.tv_weightValue) as TextView
        val displaySexVal = inf.findViewById<View>(R.id.tv_sexValue) as TextView
        val displayAgeVal = inf.findViewById<View>(R.id.tv_ageValue) as TextView
        val displayActivityVal = inf.findViewById<View>(R.id.tv_activityValue) as TextView
        val displayCityVal = inf.findViewById<View>(R.id.tv_City) as TextView
        val displayCountryVal = inf.findViewById<View>(R.id.tv_Country) as TextView

        val heightSeekBar = inf.findViewById<SeekBar>(R.id.sb_heightSlider)
        val weightSeekBar = inf.findViewById<SeekBar>(R.id.sb_weightSlider)

        val ageSeekBar = inf.findViewById<SeekBar>(R.id.sb_ageSlider)
        val sexSeekBar = inf.findViewById<SeekBar>(R.id.sb_sexSlider)
        val activitySeekBar = inf.findViewById<SeekBar>(R.id.sb_activitySlider)
        val syncProfile = inf.findViewById<Button>(R.id.btn_location)


        heightSeekBar.progress = userViewModel.getUserHeight() - 56

        displayHeightVal.text = ((((heightSeekBar.progress + 56)/12 as Int).toString() + " ft, " + ((heightSeekBar.progress + 56)%12).toString() + " inch"))
        heightSeekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                //min is 4 ft 8 inch (56 inches)
                //max is 6 ft 6 inch (78 inches)
                val height = progress + 56
                val inches = height % 12
                val final = (height/12) as Int
                userHeight = progress
//                dataManager.setUserHeight(height)
//                dataManager.setUserHeightPro(userHeight)
                userViewModel.setUserHeight(height)

                displayHeightVal.text = ((final.toString() + " ft, " + inches + " inch"))
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // update height var
            }
        })

        weightSeekBar.progress = userViewModel.getUserWeight()

        displayWeightVal.text = weightSeekBar.progress.toString()
        weightSeekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                userWeight = progress
                userViewModel.setUserWeight(userWeight)

                displayWeightVal.text = progress.toString()
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // update weight var
            }
        })

        ageSeekBar.progress = userViewModel.getUserAge()

        displayAgeVal.text = ageSeekBar.progress.toString()
        ageSeekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                userAge = progress
                userViewModel.setUserAge(progress)

                displayAgeVal.text = progress.toString()
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // update age var
            }
        })

        when (userViewModel.getUserSex()){

            "Female" -> {
                sexSeekBar.progress = 0
                displaySexVal.text = "Female"
            }
            "Other" -> {
                sexSeekBar.progress = 1
                displaySexVal.text = "Other"
            }
            "Male" -> {
                sexSeekBar.progress = 2
                displaySexVal.text = "Male"
            }
        }

        sexSeekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                userSex = progress
                when (progress) {
                    0 -> {displaySexVal.text = "Female"; userViewModel.setUserSex("Female")}
                    1 -> {displaySexVal.text = "Other"; userViewModel.setUserSex("Other")}
                    2 -> {displaySexVal.text = "Male"; userViewModel.setUserSex("Male")}

                }
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // update sex var
            }
        })

        activitySeekBar.progress = userViewModel.getUserActivityLevel().toInt()

        displayActivityVal.text = activitySeekBar.progress.toString()
        activitySeekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                userActivityLevel = progress
                userViewModel.setUserActivityLevel(progress)

                displayActivityVal.text = progress.toString()
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // update activity level var
            }
        })

        displayCityVal.text = userViewModel.getUserCity()
        displayCountryVal.text = userViewModel.getUserCountry()
        syncProfile.setOnClickListener{
            userCity = displayCityVal.text.toString()
            userCountry = displayCountryVal.text.toString()
            userViewModel.setUserCity(userCity)
            userViewModel.setUserCountry(userCountry)

        }
    }
    private val isExternalStorageWritable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state
        }

    private var cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val extras = result.data!!.extras
                imgProfileImage = extras!!["data"] as Bitmap?
                val filePathString = writeImage(imgProfileImage)

                image.setImageBitmap(BitmapFactory.decodeFile(filePathString))
                userViewModel.setIMGPath(filePathString)


            }

        }

    private fun writeImage(finalBitmap: Bitmap?): String {
        val directory = File("${getActivity()?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}/saved_images")
        directory.mkdirs()
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "Thumbnail_$timeStamp.jpg"
        val file = File(directory, fileName)
        if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            finalBitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            //Toast.makeText(this, "Not able to get directory!", Toast.LENGTH_SHORT)
        }
        return file.absolutePath
    }

//    override fun onSaveInstanceState(outState: Bundle){
//        super.onSaveInstanceState(outState)
//        outState.putString("firstName", firstName)
//        outState.putString("lastName", lastName)
//        outState.putInt("heightBarPro", userHeight)
//        outState.putInt("weightBarPro", userWeight)
//        outState.putInt("ageBarPro", userAge)
//        outState.putInt("sexBarPro", userSex)
//        outState.putInt("activityBarPro", userActivityLevel)
//        outState.putString("city", userCity)
//        outState.putString("country", userCountry)
//    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}