package com.example.deliveryapp.tables

import androidx.room.Embedded
import androidx.room.Relation
import com.example.deliveryapp.tables.ClientWithOrdersAndOrderElementsAndAdditions
import com.example.deliveryapp.tables.RestaurantWithOrdersAndOrderElementsAndAdditionsAndProducts
import com.example.deliveryapp.tables.Town

data class TownWithRestaurantsAndClientsAndOrdersAndOrderElementsAndAdditions(
    @Embedded val town: Town,
    @Relation(
        parentColumn = "name_town",
        entityColumn = "name_town"
    )
    val clients: List<ClientWithOrdersAndOrderElementsAndAdditions>,
    @Relation(
        parentColumn = "name_town",
        entityColumn = "name_town"
    )
    val restaurants: List<RestaurantWithOrdersAndOrderElementsAndAdditionsAndProducts>
)