package com.example.deliveryapp.main_activity_and_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.deliveryapp.R
import com.example.deliveryapp.viewmodels.LoginRegisterViewModel
import com.example.deliveryapp.databinding.FragmentRegisterBinding
import com.example.deliveryapp.viewmodels.FragmentRegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FragmentRegister : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel2: FragmentRegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        val bundle = arguments
        val clientOrRestaurant = bundle?.getString("clientOrRestaurantKey")

        binding.btnRegister.setOnClickListener {
            setUpButtonRegisterListener(clientOrRestaurant)
        }

        subscribeToObservables()

        return binding.root
    }

    private fun setUpButtonRegisterListener(clientOrRestaurant: String?){
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val repeatedPassword = binding.etRepeatPassword.text.toString()

        viewModel2.isInDatabase(email, password, repeatedPassword, clientOrRestaurant)
    }

    private fun goToClientData(email: String, password: String){
        val bundle = Bundle()
        bundle.putString("emailKey", email)
        bundle.putString("passwordKey", password)

        findNavController().navigate(R.id.action_fragmentRegister_to_fragmentClientData, bundle)
    }

    private fun goToRestaurantData(email: String, password: String){
        val bundle = Bundle()
        bundle.putString("emailKey", email)
        bundle.putString("passwordKey", password)

        findNavController().navigate(R.id.action_fragmentRegister_to_fragmentRestaurantData, bundle)
    }


    private fun clearEditTexts(){
        binding.etEmail.text.clear()
        binding.etPassword.text.clear()
        binding.etRepeatPassword.text.clear()
    }

    private fun enterCorrectValues(){
        Toast.makeText(activity, "Please enter correct values", Toast.LENGTH_SHORT).show()
        clearEditTexts()
    }

    private fun alreadyInDatabase(){
        Toast.makeText(activity, "This profile already exists in database", Toast.LENGTH_SHORT).show()
        clearEditTexts()
    }

    private fun wrongRepeatedPassword(){
        Toast.makeText(activity, "Please repeat your password correctly", Toast.LENGTH_SHORT).show()
        clearEditTexts()
    }

    private fun subscribeToObservables(){
        lifecycleScope.launchWhenStarted{
            viewModel2.goToActivityRestaurant.collectLatest {
                val restaurant = viewModel2.getRestaurant()
                goToRestaurantData(restaurant?.email.toString(), restaurant?.password.toString())
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel2.toastEmptyField.collectLatest {
                enterCorrectValues()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel2.goToActivityClient.collectLatest {
                val client = viewModel2.getClient()
                goToClientData(client?.email.toString(), client?.password.toString())
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel2.alreadyInDatabase.collectLatest {
                alreadyInDatabase()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel2.wrongRepeatedPassword.collectLatest {
                wrongRepeatedPassword()
            }
        }
    }
}