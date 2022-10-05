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
import androidx.appcompat.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.deliveryapp.tables.Restaurant
import com.example.deliveryapp.LoginRegisterViewModel
import com.example.deliveryapp.databinding.FragmentRestaurantDataBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentRestaurantData : Fragment() {
    private lateinit var binding: FragmentRestaurantDataBinding
    private val viewModel: LoginRegisterViewModel by activityViewModels()
    private var imageUri: Uri? = null

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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

        populateSpinner()

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
        val listTowns = mutableListOf<String>()
        val adapter = ArrayAdapter(requireActivity(), R.layout.support_simple_spinner_dropdown_item,
            listTowns)
        binding.spnTown.adapter = adapter

        viewModel.getTowns().observe(viewLifecycleOwner) { towns ->
            listTowns.clear()
            for (town in towns) {
                listTowns.add(town.name_town)
            }
            adapter.notifyDataSetChanged()
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

    private fun setUpButtonSaveListener(email: String?, password: String?){
        val name = binding.etName.text.toString()
        val adress = binding.etAdress.text.toString()
        val description = binding.etDescription.text.toString()
        val phoneNumber = binding.etPhoneNumber.text.toString()

        if(name.isEmpty() || adress.isEmpty() || description.isEmpty() || phoneNumber.isEmpty()){
            Toast.makeText(activity, "One or more required fields are empty", Toast.LENGTH_SHORT).show()
            clearEditTexts()
        }
        else if(binding.spnTown.selectedItemPosition == 0){
            Toast.makeText(activity, "Select a town", Toast.LENGTH_SHORT).show()
        }
        else{
            val restaurant = viewModel.getRestaurant(email, password)

            if(restaurant?.email == email && restaurant?.password == password){
                Toast.makeText(activity, "This restaurant already exists in the database", Toast.LENGTH_SHORT).show()
                clearEditTexts()
            }
            else{
                viewModel.insertRestaurant(
                    Restaurant(name, adress, description, phoneNumber, email.toString(),
                    password.toString(), imageUri.toString(), binding.spnTown.selectedItem.toString())
                )
            }
        }
    }

    private fun openImagePicker(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"

        resultLauncher.launch(intent)
    }

    private fun clearEditTexts(){
        binding.etName.text.clear()
        binding.etAdress.text.clear()
        binding.etDescription.text.clear()
        binding.etPhoneNumber.text.clear()
    }
}