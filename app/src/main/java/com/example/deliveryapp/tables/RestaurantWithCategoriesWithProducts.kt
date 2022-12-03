package com.example.deliveryapp.tables

import androidx.room.Embedded
import androidx.room.Relation

data class RestaurantWithCategoriesWithProducts (
    @Embedded val restaurant: Restaurant,
    @Relation(
        entity = Category::class,
        parentColumn = "id_restaurant",
        entityColumn = "id_restaurant"
    )
    var categories: List<CategoryWithProducts?>
)