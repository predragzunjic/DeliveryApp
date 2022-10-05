package com.example.deliveryapp.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class Order(
    var cost: Double,
    var delivery_address: String,
    var payment_way: String,
    var date: String,
    var delivery_time: String,
    val id_client: Int,
    val id_restaurant: Int
){
    @PrimaryKey(autoGenerate = true)
    var id_order: Int? = null
}