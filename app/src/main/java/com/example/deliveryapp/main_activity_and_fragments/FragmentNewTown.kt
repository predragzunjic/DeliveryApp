package com.example.deliveryapp.main_activity_and_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.deliveryapp.LoginRegisterViewModel
import com.example.deliveryapp.tables.Town
import com.example.deliveryapp.databinding.FragmentNewTownBinding

class FragmentNewTown : Fragment() {
    private lateinit var binding: FragmentNewTownBinding
    private val viewModel: LoginRegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTownBinding.inflate(layoutInflater)

        var listTowns = mutableListOf<String>()
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

        binding.btnConfirm.setOnClickListener {
            setUpButtonConfirmListener(listTowns)
        }

        return binding.root
    }

    private fun setUpButtonConfirmListener(listTowns: MutableList<String>){
        val town = binding.etEnterTown.text.toString()
        var isOnList = false

        for(townFromList in listTowns){
            if(town == townFromList){
                isOnList = true
            }
        }

        if(!isOnList)
            viewModel.insertTown(Town(town))
        else
            Toast.makeText(activity, "This town already exists in the database", Toast.LENGTH_SHORT).show()
    }
}