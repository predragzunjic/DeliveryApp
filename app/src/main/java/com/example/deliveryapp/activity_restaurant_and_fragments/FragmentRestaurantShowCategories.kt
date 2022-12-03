package com.example.deliveryapp.activity_restaurant_and_fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.CategoriesAdapter
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.AlertBodyAddCategoryBinding
import com.example.deliveryapp.databinding.FragmentRestaurantShowCategoriesBinding
import com.example.deliveryapp.tables.Category
import com.example.deliveryapp.viewmodels.LoggedInViewModel


class FragmentRestaurantShowCategories : Fragment() {
    private lateinit var binding: FragmentRestaurantShowCategoriesBinding
    private val viewModel: LoggedInViewModel by activityViewModels()
    private var idRestaurant: Int? = null
    private lateinit var listCategories: MutableList<Category?>
    private var alertDialog: AlertDialog? = null
    private lateinit var alertBodyAddCategoryBinding: AlertBodyAddCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantShowCategoriesBinding.inflate(layoutInflater)

        val activity: Activity? = activity
        val myActivity: ActivityRestaurant? = activity as ActivityRestaurant?
        idRestaurant = myActivity?.getFragmentRestaurantShowCategoriesValue()

        val adapter = CategoriesAdapter(arrayListOf()){category ->
            val bundle = Bundle()
            bundle.putString("idRestaurant", idRestaurant.toString())
            bundle.putString("idCategory", category?.id_category.toString())
            findNavController().navigate(R.id.action_fragmentRestaurantShowCategories_to_fragmentRestaurantShowProducts
                , bundle)
        }

        binding.rvCategories.adapter = adapter
        binding.rvCategories.layoutManager = LinearLayoutManager(activity)

        viewModel.getRestaurantWithCategoryWithProducts(idRestaurant)
            .observe(viewLifecycleOwner) { categories ->

                listCategories = mutableListOf()

                categories?.firstOrNull()?.categories?.forEach {
                    listCategories.add(it?.category)
                }


                adapter.submitList(listCategories)
        }

        alertBodyAddCategoryBinding = AlertBodyAddCategoryBinding.inflate(layoutInflater)

        binding.fabAddCategory.setOnClickListener {
            setUpFabAddCategoryListener()
        }

        return binding.root
    }

    private fun setUpFabAddCategoryListener(){
        if(alertDialog == null){
            alertDialog = AlertDialog.Builder(requireContext())
                .setTitle("Enter a new category")
                .setView(alertBodyAddCategoryBinding.root)
                .show()
        }
        alertDialog?.show()

        setUpFabAddAlertDialogBtnAdd()

        setUpFabAddAlertDialogBtnCancel()

        alertBodyAddCategoryBinding.etCategory.requestFocus()
    }

    private fun setUpFabAddAlertDialogBtnAdd(){
        alertBodyAddCategoryBinding.btnAdd.setOnClickListener {
            val category = alertBodyAddCategoryBinding.etCategory.text.toString()

            when{
                category.isEmpty() -> Toast.makeText(requireContext(), "Please enter correct values",
                    Toast.LENGTH_SHORT).show()

                else -> {
                    if(listCategories.isNotEmpty())
                        for(i in listCategories){
                            if(i?.name_category == category){
                                Toast.makeText(requireContext(), "This category already exists!",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }


                    val categoryObject = Category(category, idRestaurant!!)

                    viewModel.insertCategory(categoryObject)

                    alertBodyAddCategoryBinding.etCategory.setText("")

                    alertDialog?.dismiss()
                }
            }
        }
    }

    private fun setUpFabAddAlertDialogBtnCancel(){
        alertBodyAddCategoryBinding.btnCancel.setOnClickListener {
            alertDialog?.dismiss()
        }
    }
}