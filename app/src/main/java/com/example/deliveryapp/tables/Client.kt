package com.example.deliveryapp.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Client(
    var name_client: String,
    var surname: String,
    var adress: String,
    var email: String?,
    var password: String?,
    var name_town: String
){
    @PrimaryKey(autoGenerate = true)
    var id_client: Int? = null
}