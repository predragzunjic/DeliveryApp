package com.example.deliveryapp.activity_client_and_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.LoggedInViewModel
import com.example.deliveryapp.RestaurantsAdapter
import com.example.deliveryapp.databinding.FragmentClientShowRestaurantsBinding

class FragmentClientShowRestaurants : Fragment() {
    private lateinit var binding: FragmentClientShowRestaurantsBinding
    private val viewModel: LoggedInViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClientShowRestaurantsBinding.inflate(layoutInflater)

        val adapter = RestaurantsAdapter(arrayListOf())

        binding.rvRestaurants.adapter = adapter
        binding.rvRestaurants.layoutManager = LinearLayoutManager(activity)

        viewModel.getRestaurants().observe(viewLifecycleOwner){restaurants ->
            adapter.submitList(restaurants)
        }

        return binding.root
    }
}