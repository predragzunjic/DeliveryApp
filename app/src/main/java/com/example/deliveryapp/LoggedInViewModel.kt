package com.example.deliveryapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.deliveryapp.repositories.LoggedInRepository
import com.example.deliveryapp.tables.Category
import com.example.deliveryapp.tables.Restaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoggedInViewModel @Inject constructor(
    private val repository: LoggedInRepository
) : ViewModel() {
    private lateinit var restaurants: LiveData<List<Restaurant>>
    private lateinit var categories: LiveData<List<Category>>

    fun getRestaurants(): LiveData<List<Restaurant>> {
        privateGetRestaurants()

        return restaurants
    }

    fun getCategories(): LiveData<List<Category>>{
        privateGetCategories()

        return categories
    }

    private fun privateGetCategories(){
        categories = repository.getCategories().asLiveData()
    }

    private fun privateGetRestaurants(){
        restaurants = repository.getRestaurants().asLiveData()
    }
}