package com.example.deliveryapp.main_activity_and_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.deliveryapp.tables.Client
import com.example.deliveryapp.viewmodels.LoginRegisterViewModel
import com.example.deliveryapp.activity_client_and_fragments.ActivityClient
import com.example.deliveryapp.databinding.FragmentClientDataBinding
import com.example.deliveryapp.viewmodels.FragmentClientDataViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FragmentClientData : Fragment() {
    private lateinit var binding: FragmentClientDataBinding
    private val viewModel2: FragmentClientDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClientDataBinding.inflate(layoutInflater)

        val bundle = arguments
        val email = bundle?.getString("emailKey")
        val password = bundle?.getString("passwordKey")

        setUpSpinnerListener()

        subscribeToObservables()

        binding.btnSave.setOnClickListener {
            setUpButtonSaveListener(email, password)
        }

        return binding.root
    }

    private fun setUpButtonSaveListener(email: String?, password: String?){
        val name = binding.etName.text.toString()
        val surname = binding.etSurname.text.toString()
        val address = binding.etAdress.text.toString()

        viewModel2.insertClient(email, password, name, surname, address,
            binding.spnTown.selectedItem.toString())
    }

    private fun goToActivityClient(idClient: Int?){
        activity?.let{
            val intent = Intent(it, ActivityClient::class.java)
            intent.putExtra("idClient", idClient)
            intent.putExtra("nameTown", binding.spnTown.selectedItem.toString())
            it.startActivity(intent)
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

    private fun populateSpinner(){
        var listTowns = mutableListOf<String>()
        val adapter = ArrayAdapter(requireActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            listTowns)
        binding.spnTown.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel2.towns.collectLatest{ towns ->
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

    private fun clearEditTexts(){
        binding.etName.text.clear()
        binding.etSurname.text.clear()
        binding.etAdress.text.clear()
    }

    private fun toastFieldsEmpty(){
        Snackbar.make(binding.root, "One or more required fields are empty", Snackbar.LENGTH_LONG).show()
        clearEditTexts()
    }

    private fun subscribeToObservables(){
        populateSpinner()

        lifecycleScope.launchWhenStarted {
            viewModel2.goToActivityClient.collectLatest {
                goToActivityClient(viewModel2.getClientId())
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel2.toastEmptyField.collectLatest {
                toastFieldsEmpty()
            }
        }
    }
}