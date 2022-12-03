package com.example.deliveryapp.activity_restaurant_and_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.ProductsAdapter
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.FragmentRestaurantShowProductsBinding
import com.example.deliveryapp.tables.CategoryWithProducts
import com.example.deliveryapp.tables.Product
import com.example.deliveryapp.viewmodels.LoggedInViewModel

class FragmentRestaurantShowProducts : Fragment() {
    private lateinit var binding: FragmentRestaurantShowProductsBinding
    private val viewModel: LoggedInViewModel by activityViewModels()
    private var idRestaurant: Int? = null
    private var idCategory: Int? = null
    private lateinit var listProducts: MutableList<Product?>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantShowProductsBinding.inflate(layoutInflater)

        val bundle = arguments
        idRestaurant = bundle?.getString("idRestaurant")?.toInt()
        idCategory = bundle?.getString("idCategory")?.toInt()

        val adapter = ProductsAdapter(arrayListOf())

        binding.rvProducts.adapter = adapter
        binding.rvProducts.layoutManager = LinearLayoutManager(activity)

        viewModel.getRestaurantWithCategoryWithProducts(idRestaurant)
            .observe(viewLifecycleOwner) { categories ->
                listProducts = mutableListOf()

                var category: CategoryWithProducts? = null

                for(i in categories.first()?.categories.orEmpty()){
                    if(i?.category?.id_category == idCategory){
                        category = i
                    }
                }

                for(i in category?.products.orEmpty()){
                    listProducts.add(i)
                }
                adapter.submitList(listProducts)
            }

        binding.fabAddProduct.setOnClickListener {
            setUpFabAddCategoryListener()
        }

        return binding.root
    }

    private fun setUpFabAddCategoryListener(){
        val bundle = Bundle()
        bundle.putString("idRestaurant", idRestaurant.toString())
        bundle.putString("idCategory", idCategory.toString())
        findNavController().navigate(
            R.id.action_fragmentRestaurantShowProducts_to_fragmentRestaurantAddProduct
            , bundle)
    }
}