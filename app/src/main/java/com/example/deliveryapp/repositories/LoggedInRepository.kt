package com.example.deliveryapp.repositories

import com.example.deliveryapp.daos.RestaurantDao
import com.example.deliveryapp.daos.TownDao
import com.example.deliveryapp.tables.Category
import com.example.deliveryapp.tables.Product
import com.example.deliveryapp.tables.Restaurant
import javax.inject.Inject

class LoggedInRepository @Inject constructor(
    private val restaurantDao: RestaurantDao,
    private val townDao: TownDao
) {

    suspend fun insertRestaurant(restaurant: Restaurant) = restaurantDao.insertRestaurant(restaurant)

    fun getRestaurantWithCategoriesWithProducts(idRestaurant: Int?) =
        restaurantDao.getRestaurantWithCategoriesWithProducts(idRestaurant)

    suspend fun getRestaurant(email: String?, password: String?) = restaurantDao
        .getRestaurant(email, password)

    fun getRestaurants() = restaurantDao.getRestaurants()

    fun getRestaurantsInTown(nameTown: String?) = townDao.getRestaurantsInTown(nameTown)

    fun getRestaurantWithCategoriesWithProductsBoolean(idRestaurant: Int?, idCategory: Int?) =
        restaurantDao.getRestaurantWithCategoriesWithProductsBoolean(idRestaurant, idCategory)

    suspend fun getTownWithClient(idClient: Int?) = townDao.getTownWithClient(idClient)

    suspend fun insertProduct(product: Product) = restaurantDao.insertProduct(product)

    suspend fun insertCategory(category: Category) = restaurantDao.insertCategory(category)

    suspend fun deleteAllCategories() = restaurantDao.deleteAllCategories()
}