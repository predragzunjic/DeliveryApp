package com.example.deliveryapp.tables

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class CategoryWithProducts(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id_category",
        entityColumn = "id_category"
    )
    var products: List<Product?>
)