package com.example.deliveryapp.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrderElement(
    var quantity: Double,
    var note: String,
    var id_order: Int
){
    @PrimaryKey(autoGenerate = true)
    var id_order_element: Int? = null
}