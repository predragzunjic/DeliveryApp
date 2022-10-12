package com.example.deliveryapp

import androidx.recyclerview.widget.DiffUtil
import com.example.deliveryapp.tables.Restaurant

class DiffCallbackRestaurant(
    private val oldList: List<Restaurant>,
    var newList: List<Restaurant>
): DiffUtil.Callback(){

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.email == newItem.email && oldItem.name_restaurant == newItem.name_restaurant &&
                oldItem.name_town == newItem.name_town && oldItem.adress == newItem.adress
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return compareContents(oldItem, newItem)
    }

    private fun compareContents(oldItem: Restaurant, newItem: Restaurant): Boolean {
        if(oldItem.email == newItem.email && oldItem.name_restaurant == newItem.name_restaurant &&
            oldItem.name_town == newItem.name_town  && oldItem.adress == newItem.adress)
            return true

        return false
    }

}