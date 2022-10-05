package com.example.deliveryapp.activity_restaurant_and_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.LoggedInViewModel
import com.example.deliveryapp.R
import com.example.deliveryapp.RestaurantsAdapter
import com.example.deliveryapp.databinding.ActivityClientBinding.inflate
import com.example.deliveryapp.databinding.ActivityMainBinding.inflate
import com.example.deliveryapp.databinding.FragmentClientShowRestaurantsBinding
import com.example.deliveryapp.databinding.FragmentRestaurantShowDishesBinding

class FragmentRestaurantShowDishes : Fragment() {
    private lateinit var binding: FragmentRestaurantShowDishesBinding
    private val viewModel: LoggedInViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantShowDishesBinding.inflate(layoutInflater)

        val adapter = RestaurantsAdapter(arrayListOf())

        binding.rvRestaurants.adapter = adapter
        binding.rvRestaurants.layoutManager = LinearLayoutManager(activity)

        viewModel.getRestaurants().observe(viewLifecycleOwner){restaurants ->
            adapter.submitList(restaurants)
        }

        return binding.root

}