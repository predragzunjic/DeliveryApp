package com.example.deliveryapp.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Addition(
    @PrimaryKey(autoGenerate = false)
    var name_addition: String,
    var price: Double,
    var id_order_element: Int,
    var id_restaurant: Int
)