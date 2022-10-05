package com.example.deliveryapp.repositories

import com.example.deliveryapp.daos.RestaurantDao
import com.example.deliveryapp.tables.Restaurant
import javax.inject.Inject

class LoggedInRepository @Inject constructor(
    private val restaurantDao: RestaurantDao
) {

    suspend fun insertRestaurant(restaurant: Restaurant) = restaurantDao.insertRestaurant(restaurant)

    fun getRestaurants() = restaurantDao.getRestaurants()

    suspend fun getRestaurant(email: String?, password: String?) = restaurantDao.getRestaurant(email, password)
}