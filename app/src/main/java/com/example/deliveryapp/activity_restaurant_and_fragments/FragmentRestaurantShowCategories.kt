package com.example.deliveryapp.activity_restaurant_and_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.CategoriesAdapter
import com.example.deliveryapp.LoggedInViewModel
import com.example.deliveryapp.databinding.FragmentRestaurantShowCategoriesBinding

class FragmentRestaurantShowCategories : Fragment() {
    private lateinit var binding: FragmentRestaurantShowCategoriesBinding
    private val viewModel: LoggedInViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantShowCategoriesBinding.inflate(layoutInflater)

        val adapter = CategoriesAdapter(arrayListOf())

        binding.rvCategories.adapter = adapter
        binding.rvCategories.layoutManager = LinearLayoutManager(activity)

        viewModel.getCategories().observe(viewLifecycleOwner) { categories ->
            adapter.submitList(categories)
        }

        return binding.root
    }

}