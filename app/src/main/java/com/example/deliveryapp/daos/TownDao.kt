package com.example.deliveryapp.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.deliveryapp.tables.Town
import kotlinx.coroutines.flow.Flow

@Dao
interface TownDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTown(town: Town)

    @Query("SELECT name_town FROM town")
    fun getTowns(): Flow<List<Town>>

    @Query("DELETE FROM town WHERE name_town = :name_town")
    suspend fun deleteTown(name_town: String)
}