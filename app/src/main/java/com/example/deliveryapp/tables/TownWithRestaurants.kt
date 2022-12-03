package com.example.deliveryapp.tables

import androidx.room.Embedded
import androidx.room.Relation

data class TownWithRestaurants(
    @Embedded val town: Town,
    @Relation(
        parentColumn = "name_town",
        entityColumn = "name_town"
    )
    val restaurants: List<Restaurant?>
)
