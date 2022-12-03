package com.example.deliveryapp.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.deliveryapp.tables.Client

@Dao
interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClient(client: Client)

    @Query("SELECT * FROM client WHERE email = :email AND password = :password")
    suspend fun getClient(email: String, password: String): Client?

    @Query("SELECT EXISTS(SELECT * FROM client WHERE email = :email AND password = :password)")
    suspend fun getClient2(email: String, password: String): Boolean
}