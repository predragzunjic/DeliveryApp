package com.example.deliveryapp.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.deliveryapp.tables.Restaurant
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurant: Restaurant)

    @Query("SELECT * FROM restaurant")
    fun getRestaurants(): Flow<List<Restaurant>>

    @Query("SELECT * FROM restaurant WHERE email = :email AND password = :password")
    suspend fun getRestaurant(email: String?, password: String?): Restaurant
}