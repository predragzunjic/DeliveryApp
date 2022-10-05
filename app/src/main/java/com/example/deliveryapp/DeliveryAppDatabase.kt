package com.example.deliveryapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.deliveryapp.daos.ClientDao
import com.example.deliveryapp.daos.RestaurantDao
import com.example.deliveryapp.daos.TownDao
import com.example.deliveryapp.tables.*

@Database(
    entities = [Addition::class,
    Category::class,
    Client::class,
    Order::class,
    OrderElement::class,
    Product::class,
    Restaurant::class,
    Town::class,
    RestaurantAdditionCrossRef::class
    ],
    version = 4
)
abstract class DeliveryAppDatabase: RoomDatabase() {
    abstract fun getDao(): DeliveryAppDao

    abstract fun getClientDao(): ClientDao

    abstract fun getRestaurantDao(): RestaurantDao

    abstract fun getTownDao(): TownDao
}