package com.example.deliveryapp.tables

import androidx.room.Entity

@Entity(primaryKeys = ["id_restaurant", "name_addition"])
data class RestaurantAdditionCrossRef(
    val id_restaurant: Int,
    val name_addition: String
    )