package com.example.deliveryapp.main_activity_and_fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.deliveryapp.activity_restaurant_and_fragments.ActivityRestaurant
import com.example.deliveryapp.databinding.FragmentRestaurantDataBinding
import com.example.deliveryapp.viewmodels.FragmentRestaurantDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest



@AndroidEntryPoint
class FragmentRestaurantData : Fragment() {
    private lateinit var binding: FragmentRestaurantDataBinding
    private val viewModel2: FragmentRestaurantDataViewModel by viewModels()
    private var imageUri: Uri? = null

    private var resultLauncher = registerForActivityResult(ActivityResultContracts
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
        binding = FragmentRestaurantDataBinding.inflate(layoutInflater)
        val bundle = arguments
        val email = bundle?.getString("emailKey")
        val password = bundle?.getString("passwordKey")

        subscribeToObservables()

        setUpSpinnerListener()

        binding.btnAddImage.setOnClickListener {
            openImagePicker()
        }

        binding.btnSave.setOnClickListener {
            setUpButtonSaveListener(email, password)
        }

        return binding.root
    }

    private fun populateSpinner(){
        var listTowns = mutableListOf<String>()
        val adapter = ArrayAdapter(requireActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            listTowns)
        binding.spnTown.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel2.towns.collectLatest { towns ->
                if(towns.isNullOrEmpty()){
                    listTowns.clear()
                }
                else{
                    listTowns.clear()

                    for (town in towns) {
                        listTowns.add(town.toString())
                    }
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun setUpSpinnerListener(){
        binding.spnTown.setSelection(0)
        binding.spnTown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setUpButtonSaveListener(email: String?, password: String?) {
        val name = binding.etName.text.toString()
        val address = binding.etAdress.text.toString()
        val description = binding.etDescription.text.toString()
        val phoneNumber = binding.etPhoneNumber.text.toString()

        viewModel2.insertRestaurant(
            email, password, name, address, description,
            phoneNumber, imageUri.toString(), binding.spnTown.selectedItem.toString()
        )
    }

    private fun openImagePicker(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"

        resultLauncher.launch(intent)
    }

    private fun toastEmptyField(){
        Toast.makeText(activity, "One or more required fields are empty", Toast.LENGTH_SHORT).show()

        clearEditTexts()
    }

    private fun goToActivityRestaurant(idRestaurant: Int?){
        activity?.let{
            val intent = Intent (it, ActivityRestaurant::class.java)
            intent.putExtra("idRestaurant", idRestaurant)
            it.startActivity(intent)
        }
    }

    private fun clearEditTexts(){
        binding.etName.text.clear()
        binding.etAdress.text.clear()
        binding.etDescription.text.clear()
        binding.etPhoneNumber.text.clear()
    }

    private fun subscribeToObservables(){
        populateSpinner()
        
        lifecycleScope.launchWhenStarted {
            viewModel2.toastEmptyField.collectLatest {
                toastEmptyField()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel2.goToActivityRestaurant.collectLatest {
                goToActivityRestaurant(viewModel2.getRestaurantId())
            }
        }
    }
}