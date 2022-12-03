package com.example.deliveryapp

import androidx.recyclerview.widget.DiffUtil
import com.example.deliveryapp.tables.Category
import com.example.deliveryapp.tables.Product

class DiffCallbackProducts(
    private val oldList: List<Product?>,
    var newList: List<Product?>
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

        return oldItem?.id_product == newItem?.id_product &&
                oldItem?.id_category == newItem?.id_category &&
                oldItem?.name_product == newItem?.name_product
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return compareContents(oldItem, newItem)
    }

    private fun compareContents(oldItem: Product?, newItem: Product?): Boolean {
        if(oldItem?.id_product == newItem?.id_product &&
            oldItem?.id_category == newItem?.id_category &&
            oldItem?.name_product == newItem?.name_product)
            return true

        return false
    }
}