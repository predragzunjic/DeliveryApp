package com.example.deliveryapp.activity_restaurant_and_fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.deliveryapp.viewmodels.LoggedInViewModel
import com.example.deliveryapp.databinding.ActivityRestaurantBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityRestaurant : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantBinding
    private val viewModel: LoggedInViewModel by viewModels()
    private var idRestaurant = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var intent = intent

        idRestaurant = intent.getIntExtra("idRestaurant", 0)

        val layoutInflater = LayoutInflater.from(this)
        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun getFragmentRestaurantShowCategoriesValue(): Int{
        return idRestaurant
    }
}