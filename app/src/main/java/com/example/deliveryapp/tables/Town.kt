package com.example.deliveryapp.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Town(
    @PrimaryKey(autoGenerate = false)
    var name_town: String
)
