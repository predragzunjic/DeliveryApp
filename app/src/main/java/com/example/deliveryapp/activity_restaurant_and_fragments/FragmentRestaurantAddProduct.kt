package com.example.deliveryapp.activity_restaurant_and_fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.deliveryapp.databinding.FragmentRestaurantAddProductBinding
import com.example.deliveryapp.databinding.FragmentRestaurantShowProductsBinding
import com.example.deliveryapp.tables.Product
import com.example.deliveryapp.viewmodels.LoggedInViewModel


class FragmentRestaurantAddProduct : Fragment() {
    private lateinit var binding: FragmentRestaurantAddProductBinding
    private val viewModel: LoggedInViewModel by activityViewModels()
    private var idRestaurant: Int? = null
    private var idCategory: Int? = null
    private var imageUri: Uri? = null

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts
        .StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            if(data != null){
                imageUri = data.data
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantAddProductBinding.inflate(layoutInflater)

        val bundle = arguments
        idRestaurant = bundle?.getString("idRestaurant")?.toInt()
        idCategory = bundle?.getString("idCategory")?.toInt()

        binding.btnAddProduct.setOnClickListener {
            setUpBtnAddProductListener()
        }

        binding.btnAddImage.setOnClickListener {
            openImagePicker()
        }

        return binding.root
    }

    private fun openImagePicker(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"

        resultLauncher.launch(intent)
    }

    private fun setUpBtnAddProductListener(){
        val nameProduct = binding.etNameProduct.text.toString()
        val price = binding.etPrice.text.toString()
        val sale = binding.etSale.text.toString()
        val description = binding.etDescription.text.toString()

        Log.d("tag1", viewModel.getRestaurantWithCategoryWithProductsBoolean(idRestaurant, idCategory).toString())

        when{
            nameProduct.isEmpty() || price.isEmpty() || description.isEmpty() ->
                Toast.makeText(requireContext(), "One or more required fields are empty",
                    Toast.LENGTH_SHORT).show()


            viewModel.getRestaurantWithCategoryWithProductsBoolean(idRestaurant, idCategory) == true -> {
                Toast.makeText(requireContext(), "This product already exists in the database",
                    Toast.LENGTH_SHORT).show()
            }

            sale.isNullOrEmpty() -> {
                var product = Product(nameProduct, price.toDouble(), 0, description, false
                    , imageUri.toString(), idCategory!!.toInt())
            }

            else -> {
                var product = Product(nameProduct, price.toDouble(), sale.toInt(), description, true
                    , imageUri.toString(), idCategory!!.toInt())

            }
        }







    }
}