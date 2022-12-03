package com.example.deliveryapp.repositories

import com.example.deliveryapp.daos.ClientDao
import com.example.deliveryapp.daos.RestaurantDao
import com.example.deliveryapp.daos.TownDao
import com.example.deliveryapp.tables.Client
import com.example.deliveryapp.tables.Restaurant
import com.example.deliveryapp.tables.Town
import javax.inject.Inject

class LoginRegisterRepository @Inject constructor(
    private val clientDao: ClientDao,
    private val restaurantDao: RestaurantDao,
    private val townDao: TownDao
) {
    suspend fun insertClient(client: Client) = clientDao.insertClient(client)

    suspend fun getClient(email: String, password: String) = clientDao.getClient(email, password)

    suspend fun insertRestaurant(restaurant: Restaurant) = restaurantDao.insertRestaurant(restaurant)

    fun getRestaurants() = restaurantDao.getRestaurants()

    suspend fun getRestaurant(email: String?, password: String?) = restaurantDao.getRestaurant(email, password)

    suspend fun insertTown(town: Town) = townDao.insertTown(town)

    fun getTowns() = townDao.getTowns()

    suspend fun deleteTown(town_name: String) = townDao.deleteTown(town_name)

    suspend fun deleteAllRestaurants() = restaurantDao.deleteAllRestaurants()

    suspend fun getRestaurant2(email: String, password: String) = restaurantDao.getRestaurant2(email, password)

    suspend fun getClient2(email: String, password: String) = clientDao.getClient2(email, password)

    suspend fun townInDatabase(nameTown: String) = townDao.townInDatabase(nameTown)
}