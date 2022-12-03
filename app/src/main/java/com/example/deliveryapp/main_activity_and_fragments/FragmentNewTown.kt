package com.example.deliveryapp.main_activity_and_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.example.deliveryapp.databinding.FragmentNewTownBinding
import com.example.deliveryapp.viewmodels.FragmentNewTownViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FragmentNewTown : Fragment() {
    private lateinit var binding: FragmentNewTownBinding
    private val viewModel2: FragmentNewTownViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTownBinding.inflate(layoutInflater)

        val listTowns = mutableListOf<String>()
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

        binding.btnConfirm.setOnClickListener {
            setUpButtonConfirmListener(listTowns)
        }

        subscribeToObservables()

        return binding.root
    }

    private fun toastTownExistsInDatabase(){
        Toast.makeText(activity, "This town already exists in the database", Toast.LENGTH_SHORT).show()
        clearEditText()
    }

    private fun clearEditText(){
        binding.etEnterTown.text.clear()
    }

    private fun setUpButtonConfirmListener(listTowns: MutableList<String>){
        val nameTown = binding.etEnterTown.text.toString()

        viewModel2.insertTown(nameTown)
    }

    private fun subscribeToObservables(){
        lifecycleScope.launchWhenStarted {
            viewModel2.toastAlreadyInDatabase.collectLatest{
                toastTownExistsInDatabase()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel2.insertedInDatabase.collectLatest {
                clearEditText()
            }
        }
    }
}