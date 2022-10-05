package com.example.deliveryapp.main_activity_and_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.FragmentClientOrRestaurantBinding

class FragmentClientOrRestaurant : Fragment() {
    private lateinit var binding: FragmentClientOrRestaurantBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClientOrRestaurantBinding.inflate(layoutInflater)

        binding.btnClient.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("clientOrRestaurantKey", "client")
            findNavController().navigate(R.id.action_fragmentClientOrRestaurant_to_fragmentLogin, bundle)
        }

        binding.btnRestaurant.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("clientOrRestaurantKey", "restaurant")
            findNavController().navigate(R.id.action_fragmentClientOrRestaurant_to_fragmentLogin, bundle)
        }

        return binding.root
    }
}