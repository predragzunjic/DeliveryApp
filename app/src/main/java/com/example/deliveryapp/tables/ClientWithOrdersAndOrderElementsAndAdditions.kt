package com.example.deliveryapp.tables

import androidx.room.Embedded
import androidx.room.Relation

data class ClientWithOrdersAndOrderElementsAndAdditions(
    @Embedded val client: Client,
    @Relation(
        entity = Order::class,
        parentColumn = "id_client",
        entityColumn = "id_client"
    )
    val orders: List<OrderWithOrderElementsAndAdditions>
)