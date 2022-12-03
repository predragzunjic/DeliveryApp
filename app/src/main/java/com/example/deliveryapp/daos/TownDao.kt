package com.example.deliveryapp.daos

import androidx.room.*
import com.example.deliveryapp.tables.Town
import com.example.deliveryapp.tables.TownWithRestaurants
import kotlinx.coroutines.flow.Flow

@Dao
interface TownDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTown(town: Town)

    @Transaction
    @Query("SELECT * FROM town WHERE name_town = :nameTown")
    fun getRestaurantsInTown(nameTown: String?): Flow<List<TownWithRestaurants>>

    @Query("SELECT name_town FROM town")
    fun getTowns(): Flow<List<String?>>

    @Query("SELECT client.name_town FROM client WHERE id_client = :idClient")
    suspend fun getTownWithClient(idClient: Int?): String?

    @Query("DELETE FROM town WHERE name_town = :name_town")
    suspend fun deleteTown(name_town: String)

    @Query("SELECT EXISTS(SELECT name_town FROM town WHERE name_town = :nameTown)")
    suspend fun townInDatabase(nameTown: String): Boolean
}