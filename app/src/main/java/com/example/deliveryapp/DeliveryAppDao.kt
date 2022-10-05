package com.example.deliveryapp

import androidx.room.*
import com.example.deliveryapp.tables.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DeliveryAppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTown(town: Town)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClient(client: Client)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddition(addition: Addition)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderElement(orderElement: OrderElement)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurant: Restaurant)

    //@Transactionr
    //@Query("SELECT * FROM restaurant WHERE id_restaurant = :id")
    //fun getRestaurantWithOrdersAndOrderElementsAndAdditionsAndProducts(id: Int):
    //LiveData<List<RestaurantWithOrdersAndOrderElementsAndAdditionsAndProducts>>

    @Query("SELECT * FROM restaurant")
    fun getRestaurants(): Flow<List<Restaurant>>

    @Query("SELECT name_town FROM town")
    fun getTowns(): Flow<List<Town>>

    @Query("DELETE FROM town WHERE name_town = :name_town")
    suspend fun deleteTown(name_town: String)

    @Query("SELECT * FROM client WHERE email = :email AND password = :password")
    suspend fun getClient(email: String, password: String): Client?

    @Query("SELECT * FROM restaurant WHERE email = :email AND password = :password")
    suspend fun getRestaurant(email: String?, password: String?): Restaurant
}