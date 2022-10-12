package com.example.deliveryapp

import android.media.MediaRouter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.databinding.RvCategoryItemBinding
import com.example.deliveryapp.databinding.RvRestaurantItemBinding
import com.example.deliveryapp.tables.Category
import com.example.deliveryapp.tables.Product
import com.example.deliveryapp.tables.Restaurant

class CategoriesAdapter(
    var categories: ArrayList<Category>
    ): RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
        private val diffCallback = DiffCallbackCategories(categories, ArrayList())

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesAdapter.CategoryViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RvCategoryItemBinding.inflate(layoutInflater, parent, false)

            return CategoryViewHolder(binding)
        }

        override fun onBindViewHolder(holder: CategoriesAdapter.CategoryViewHolder, position: Int) {
            val curCommitment = categories[position]
            holder.bind(curCommitment)
        }

        override fun getItemCount(): Int {
            return categories.size
        }

        fun submitList(updatedList: List<Category>){
            diffCallback.newList = updatedList

            val diffResult = DiffUtil.calculateDiff(diffCallback)

            categories.clear()

            categories.addAll(updatedList)
            diffResult.dispatchUpdatesTo(this)
        }


        inner class CategoryViewHolder(private val binding: RvCategoryItemBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(category: Category){
                binding.tvNameCategory.text = category.name_category
            }
        }
}