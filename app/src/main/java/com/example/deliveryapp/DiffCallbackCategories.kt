package com.example.deliveryapp

import androidx.recyclerview.widget.DiffUtil
import com.example.deliveryapp.tables.Category
import com.example.deliveryapp.tables.Restaurant

class DiffCallbackCategories (
    private val oldList: List<Category>,
    var newList: List<Category>
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

            return oldItem.id_category == newItem.id_category &&
                    oldItem.name_category == newItem.name_category
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return compareContents(oldItem, newItem)
        }

        private fun compareContents(oldItem: Category, newItem: Category): Boolean {
            if(oldItem.id_category == newItem.id_category
                && oldItem.name_category == newItem.name_category)
                return true

            return false
        }
}