package com.example.deliveryapp.activity_client_and_fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.viewmodels.LoggedInViewModel
import com.example.deliveryapp.RestaurantsAdapter
import com.example.deliveryapp.activity_restaurant_and_fragments.ActivityRestaurant
import com.example.deliveryapp.databinding.FragmentClientShowRestaurantsBinding
import com.example.deliveryapp.tables.Category
import com.example.deliveryapp.tables.Restaurant

class FragmentClientShowRestaurants : Fragment() {
    private lateinit var binding: FragmentClientShowRestaurantsBinding
    private val viewModel: LoggedInViewModel by activityViewModels()
    private var nameTown: String? = null
    private var idClient: Int? = null
    private lateinit var listRestaurants: MutableList<Restaurant?>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClientShowRestaurantsBinding.inflate(layoutInflater)

        val activity: Activity? = activity
        val myActivity: ActivityClient? = activity as ActivityClient?
        idClient = myActivity?.getIdClient()
        nameTown = myActivity?.getNameTown()

        if(nameTown.isNullOrEmpty()){
            idClient = myActivity?.getIdClient()

            nameTown = viewModel.getTownWithClient(idClient)
        }



        val adapter = RestaurantsAdapter(arrayListOf())

        binding.rvRestaurants.adapter = adapter
        binding.rvRestaurants.layoutManager = LinearLayoutManager(activity)

        viewModel.geRestaurantsInTown(nameTown).observe(viewLifecycleOwner){restaurants ->
            listRestaurants = mutableListOf()

            restaurants.firstOrNull()?.restaurants?.forEach {
                listRestaurants.add(it)
            }

            adapter.submitList(listRestaurants)
        }

        return binding.root
    }
}