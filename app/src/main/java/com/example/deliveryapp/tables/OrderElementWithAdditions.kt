package com.example.deliveryapp.tables

import androidx.room.Embedded
import androidx.room.Relation
import com.example.deliveryapp.tables.Addition
import com.example.deliveryapp.tables.OrderElement

data class OrderElementWithAdditions(
    @Embedded val orderElement: OrderElement,
    @Relation(
        entity = Addition::class,
        parentColumn = "id_order_element",
        entityColumn = "id_order_element"
    )
    var orders: List<Addition>
)