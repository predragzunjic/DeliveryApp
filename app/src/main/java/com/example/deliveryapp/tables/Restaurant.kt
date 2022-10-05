package com.example.deliveryapp.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Restaurant(
    var name_restaurant: String,
    var adress: String,
    var description: String,
    var phone_number: String,
    var email: String,
    var password: String,
    var photo: String,
    val name_town: String
){
    @PrimaryKey(autoGenerate = true)
    var id_restaurant: Int? = null
}