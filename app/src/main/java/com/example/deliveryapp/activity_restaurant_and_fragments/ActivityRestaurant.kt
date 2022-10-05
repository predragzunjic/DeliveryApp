package com.example.deliveryapp.activity_restaurant_and_fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.deliveryapp.LoggedInViewModel
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.ActivityClientBinding
import com.example.deliveryapp.databinding.ActivityRestaurantBinding

class ActivityRestaurant : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantBinding
    private val viewModel: LoggedInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layoutInflater = LayoutInflater.from(this)
        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}