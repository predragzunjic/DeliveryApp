package com.example.deliveryapp.daos

import androidx.room.*
import com.example.deliveryapp.tables.*
import kotlinx.coroutines.flow.*

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurant: Restaurant)

    @Transaction
    @Query("SELECT * FROM restaurant WHERE id_restaurant = :idRestaurant")
    fun getRestaurantWithCategoriesWithProducts(idRestaurant: Int?):
            Flow<List<RestaurantWithCategoriesWithProducts?>>

    @Query("SELECT * FROM restaurant")
    fun getRestaurants(): Flow<List<Restaurant>>

    @Query("SELECT * FROM restaurant WHERE email = :email AND password = :password")
    suspend fun getRestaurant(email: String?, password: String?): Restaurant

    @Query("SELECT EXISTS(SELECT * FROM restaurant INNER JOIN category on " +
            "restaurant.id_restaurant = category.id_restaurant INNER JOIN product on " +
            "category.id_category = product.id_category WHERE restaurant.id_restaurant = :idRestaurant " +
            " AND category.id_category = :idCategory)")
    fun getRestaurantWithCategoriesWithProductsBoolean(idRestaurant: Int?, idCategory: Int?):
            Boolean?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Query("DELETE from category")
    suspend fun deleteAllCategories()

    @Query("DELETE from restaurant")
    suspend fun deleteAllRestaurants()

    @Query("SELECT EXISTS(SELECT * FROM restaurant WHERE email = :email AND password = :password)")
    suspend fun getRestaurant2(email: String, password: String): Boolean
}