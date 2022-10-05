package com.example.deliveryapp

import com.example.deliveryapp.tables.*
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun insertTown(town: Town)

    suspend fun insertClient(client: Client)

    suspend fun insertOrder(order: Order)

    suspend fun insertOrderElement(orderElement: OrderElement)

    suspend fun insertAddition(addition: Addition)

    suspend fun insertRestaurant(restaurant: Restaurant)

    suspend fun insertProduct(product: Product)

    suspend fun insertCategory(category: Category)

    //suspend fun getRestaurantWithOrdersAndOrderElementsAndAdditionsAndProducts(id: Int)
        //: LiveData<List<RestaurantWithOrdersAndOrderElementsAndAdditionsAndProducts>>

    fun getRestaurants(): Flow<List<Restaurant>>

    fun getTowns(): Flow<List<Town>>

    suspend fun deleteTown(town: String)

    suspend fun getClient(email: String, password: String): Client?

    suspend fun getRestaurant(email: String?, password: String?): Restaurant
}