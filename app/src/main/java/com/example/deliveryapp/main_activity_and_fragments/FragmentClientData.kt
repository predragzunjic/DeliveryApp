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
import com.example.deliveryapp.tables.Client
import com.example.deliveryapp.LoginRegisterViewModel
import com.example.deliveryapp.activity_client_and_fragments.ActivityClient
import com.example.deliveryapp.databinding.FragmentClientDataBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentClientData : Fragment() {
    private lateinit var binding: FragmentClientDataBinding
    private val viewModel: LoginRegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClientDataBinding.inflate(layoutInflater)
        val bundle = arguments
        val email = bundle?.getString("emailKey")
        val password = bundle?.getString("passwordKey")

        populateSpinner()

        setUpSpinnerListener()

        binding.btnSave.setOnClickListener {
            setUpButtonSaveListener(email, password)
        }

        return binding.root
    }

    private fun populateSpinner(){
        val listTowns = mutableListOf<String>()
        val adapter = ArrayAdapter(requireActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
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
        val surname = binding.etSurname.text.toString()
        val address = binding.etAdress.text.toString()

        if(name.isEmpty() || surname.isEmpty() || address.isEmpty()){
            Toast.makeText(activity, "One or more required fields are empty", Toast.LENGTH_SHORT).show()
            clearEditTexts()
        }
        else{
            viewModel.insertClient(Client(name, surname, address, email, password, binding.spnTown.selectedItem.toString()))
            activity?.let{
                val intent = Intent(it, ActivityClient::class.java)
                intent.putExtra("idClient",
                    viewModel.getClient(email.toString(), password.toString())?.id_client)
                it.startActivity(intent)
            }
        }
    }

    private fun clearEditTexts(){
        binding.etName.text.clear()
        binding.etSurname.text.clear()
        binding.etAdress.text.clear()
    }

}