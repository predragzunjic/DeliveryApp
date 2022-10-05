package com.example.deliveryapp.tables

import androidx.room.Embedded
import androidx.room.Relation
import com.example.deliveryapp.tables.Order
import com.example.deliveryapp.tables.OrderElement
import com.example.deliveryapp.tables.OrderElementWithAdditions

data class OrderWithOrderElementsAndAdditions(
    @Embedded val order: Order,
    @Relation(
        entity = OrderElement::class,
        parentColumn = "id_order",
        entityColumn = "id_order"
    )
    var orderElements: List<OrderElementWithAdditions>
)