package com.example.deliveryapp.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    var name_product: String,
    var price: Double,
    var sale: Int,
    var description: String,
    var isItOnSale: Boolean,
    var photo: String,
    val id_category: Int
){
    @PrimaryKey(autoGenerate = true)
    var id_product: Int? = null
}