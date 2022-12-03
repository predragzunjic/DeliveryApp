package com.example.deliveryapp.main_activity_and_fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.deliveryapp.activity_client_and_fragments.ActivityClient
import com.example.deliveryapp.R
import com.example.deliveryapp.viewmodels.LoginRegisterViewModel
import com.example.deliveryapp.activity_restaurant_and_fragments.ActivityRestaurant
import com.example.deliveryapp.databinding.FragmentLoginBinding
import com.example.deliveryapp.tables.Client
import com.example.deliveryapp.tables.Restaurant
import com.example.deliveryapp.viewmodels.FragmentLoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentLogin : Fragment() {
    private lateinit var binding:  FragmentLoginBinding
    private val viewModel: LoginRegisterViewModel by activityViewModels()
    private val viewModel2: FragmentLoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        val bundle = arguments
        val clientOrRestaurant = bundle?.getString("clientOrRestaurantKey")

        subscribeToObservables()

        binding.btnLogin.setOnClickListener{
            setUpButtonLoginListener(clientOrRestaurant)
        }

        binding.btnRegister.setOnClickListener {
            goToFragmentRegister(clientOrRestaurant)
        }

        return binding.root
    }

    private fun setUpButtonLoginListener(clientOrRestaurant: String?){
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        viewModel2.authenticate(email, password, clientOrRestaurant)
    }

    private fun clearEditTexts(){
        binding.etEmail.text.clear()
        binding.etPassword.text.clear()
    }

    private fun goToFragmentRegister(clientOrRestaurant: String?){
        val bundle = Bundle()
        bundle.putString("clientOrRestaurantKey", clientOrRestaurant)
        findNavController().navigate(R.id.action_fragmentLogin_to_fragmentRegister, bundle)
    }

    private fun goToActivityClient(idClient: Int?){
        activity?.let{
            val intent = Intent (it, ActivityClient::class.java)
            intent.putExtra("idClient", idClient)
            it.startActivity(intent)
        }
    }

    private fun goToActivityRestaurant(idRestaurant: Int?){
        activity?.let{
            val intent = Intent (it, ActivityRestaurant::class.java)
            intent.putExtra("idRestaurant", idRestaurant)
            it.startActivity(intent)
        }
    }

    private fun enterCorrectValues(){
        Snackbar.make(binding.root, "Please enter correct values", Snackbar.LENGTH_LONG).show()
        clearEditTexts()
    }

    private fun credentialsNotMatching(){
        Snackbar.make(binding.root, "One or more required fields are empty", Snackbar.LENGTH_LONG).show()
        clearEditTexts()
    }

    private fun subscribeToObservables(){
        lifecycleScope.launchWhenStarted{
            viewModel2.goToActivityRestaurant.collectLatest {
                goToActivityRestaurant(viewModel2.getIdRestaurant())
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel2.credentialsNotMatching.collectLatest {
                credentialsNotMatching()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel2.goToActivityClient.collectLatest {
                goToActivityClient(viewModel2.getIdClient())
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel2.toastEmptyField.collectLatest {
                enterCorrectValues()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel2.goToFragmentNewTown.collectLatest {
                findNavController().navigate(R.id.action_fragmentLogin_to_fragmentNewTown)
            }
        }
    }
}