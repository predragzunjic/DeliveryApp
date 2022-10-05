package com.example.deliveryapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.databinding.RvRestaurantItemBinding
import com.example.deliveryapp.tables.Restaurant

class RestaurantsAdapter(
    var restaurants: ArrayList<Restaurant>
): RecyclerView.Adapter<RestaurantsAdapter.RestaurantViewHolder>() {
    private val diffCallback = DiffCallback(restaurants, ArrayList())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvRestaurantItemBinding.inflate(layoutInflater, parent, false)

        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val curCommitment = restaurants[position]
        holder.bind(curCommitment)
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    fun submitList(updatedList: List<Restaurant>){
        diffCallback.newList = updatedList

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        restaurants.clear()

        restaurants.addAll(updatedList)
        diffResult.dispatchUpdatesTo(this)
    }


    inner class RestaurantViewHolder(private val binding: RvRestaurantItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(restaurant: Restaurant){
            binding.restaurantName.text = restaurant.name_restaurant
            binding.restaurantDescription.text = restaurant.description
            binding.restaurantAdress.text = restaurant.adress
        }
    }
}

