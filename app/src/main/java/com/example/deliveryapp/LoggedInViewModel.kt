package com.example.deliveryapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.deliveryapp.repositories.LoggedInRepository
import com.example.deliveryapp.tables.Restaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoggedInViewModel @Inject constructor(
    private val repository: LoggedInRepository
) : ViewModel() {
    private lateinit var restaurants: LiveData<List<Restaurant>>

    fun getRestaurants(): LiveData<List<Restaurant>> {
        privateGetRestaurants()

        return restaurants
    }

    private fun privateGetRestaurants(){
        restaurants = repository.getRestaurants().asLiveData()
    }
}