package com.example.lifeapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.lifeapp.MainActivity
import com.example.lifeapp.R
import com.example.lifeapp.databinding.FragmentProfileBinding
import com.example.lifeapp.userManager
import com.example.lifeapp.viewmodels.UserViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CaloriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CaloriesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var currentCalories: Number = 0
    private var calorieLimit: Number = 0

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
        val inf: View = inflater.inflate(R.layout.fragment_calories, container, false)
        val tv_calories: TextView = inf.findViewById(R.id.calorie_id)
        tv_calories.setText(formatCalories())
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
     * Formats current calories / limit for frontend
     */
    private fun formatCalories(): String{
        return getCurrentCalories() + "/" + getCalorieLimit()
    }

    /**
     * Gets current calorie limit
     */
    private fun getCalorieLimit(): String{
        calorieLimit = userViewModel.getUserBMR().toInt() * (userViewModel.getUserActivityLevel().toInt())

        return calorieLimit.toString()
    }

    /**
     * Gets current consumed calories
     */
    private fun getCurrentCalories(): String{
        return currentCalories.toString()
    }

    /**
     * Adds calories to users total calories consumed
     */
    private fun addCalories(calories: Number): Number {
        var newTotalCalories: Number
        if (calories == 0){
            return 0
        }
        else{
            newTotalCalories = (calories.toInt() + currentCalories.toInt())
            return newTotalCalories
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CaloriesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CaloriesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}