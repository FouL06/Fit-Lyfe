package com.example.lifeapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.lifeapp.MainActivity
import com.example.lifeapp.R
import com.example.lifeapp.databinding.FragmentProfileBinding
import com.example.lifeapp.userManager
import com.example.lifeapp.viewmodels.UserViewModel
import androidx.fragment.app.activityViewModels
import com.example.lifeapp.databinding.FragmentBmiBinding



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [BmiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BmiFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var dataManager : userManager
    private val userViewModel : UserViewModel by activityViewModels()
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
        val inf: View = inflater.inflate(R.layout.fragment_bmi, container, false)
        val tv_bmr: TextView = inf.findViewById(R.id.bmr_id)
        tv_bmr.setText(calculateBMR())

        return inf
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            // Set up the button click listeners
            viewModel = userViewModel
        }
    }

    /**
     * returns the current users BMI based on their information put in
     * and using the Revised Harris-Benedict BMR equation
     */
    private fun calculateBMR(): String {
        println(userViewModel.getUserHeight())
        val a: Double
        val b: Double
        val c: Double
        val d: Double
        val weight: Double = userViewModel.getUserWeight() * 0.453592 //lbs to kg
        val height: Double = userViewModel.getUserHeight() * 2.54 //inches to cm
        val age: Int = userViewModel.getUserAge()
        //val activityLevel: Double = 1.2

        if (userViewModel.getUserSex() == "Male") {

            a = 88.362
            b = 13.397
            c = 4.799
            d = 5.677
        } else {
            a = 447.593
            b = 9.247
            c = 3.098
            d = 4.33
        }
        var bmr: Double = (a + (b * weight) + (c * height) - (d * age))
        userViewModel.setUserBMR(bmr)

        return String.format("%.0f", bmr)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BmiFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BmiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}