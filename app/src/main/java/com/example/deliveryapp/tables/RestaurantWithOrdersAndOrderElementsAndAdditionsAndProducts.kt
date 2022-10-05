package com.example.deliveryapp.tables

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.deliveryapp.tables.*

data class RestaurantWithOrdersAndOrderElementsAndAdditionsAndProducts(
    @Embedded val restaurant: Restaurant,
    @Relation(
        parentColumn = "id_restaurant",
        entityColumn = "id_restaurant"
    )
    val clients: List<OrderWithOrderElementsAndAdditions>,

    @Relation(
        parentColumn = "id_restaurant",
        entityColumn = "id_restaurant",
        associateBy = Junction(RestaurantAdditionCrossRef::class)
    )
    var additions: List<Addition>,

    @Relation(
        parentColumn = "id_restaurant",
        entityColumn = "id_restaurant"
    )
    var products: List<Product>
)