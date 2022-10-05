package com.example.deliveryapp.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    var name_category: String
){
    @PrimaryKey(autoGenerate = true)
    var id_category: Int? = null
}