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
import androidx.navigation.fragment.findNavController
import com.example.deliveryapp.activity_client_and_fragments.ActivityClient
import com.example.deliveryapp.R
import com.example.deliveryapp.LoginRegisterViewModel
import com.example.deliveryapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentLogin : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginRegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        val bundle = arguments
        val clientOrRestaurant = bundle?.getString("clientOrRestaurantKey")

        binding.btnLogin.setOnClickListener{
            setUpButtonLoginListener(clientOrRestaurant)
        }

        binding.btnRegister.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("clientOrRestaurantKey", clientOrRestaurant)
            findNavController().navigate(R.id.action_fragmentLogin_to_fragmentRegister, bundle)
        }

        return binding.root
    }

    private fun setUpButtonLoginListener(clientOrRestaurant: String?){
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(activity, "Please enter correct values", Toast.LENGTH_SHORT).show()
            clearEditTexts()
        }
        else{
            if(clientOrRestaurant.equals("client")){
                val client = viewModel.getClient(email, password)
                Log.d("tag1", "${client?.email} ${client?.password}")
                Log.d("tag1", clientOrRestaurant.toString())
                if(client?.email.equals(email) && client?.password.equals(password)){
                    activity?.let{
                        val intent = Intent (it, ActivityClient::class.java)
                        it.startActivity(intent)
                    }
                }
                else if(email == "AdminAdminovic" && password == "passwordPasswordovic"){
                    findNavController().navigate(R.id.action_fragmentLogin_to_fragmentNewTown)
                }
                else{
                    Toast.makeText(activity, "Credentials don't match", Toast.LENGTH_SHORT).show()
                    clearEditTexts()
                }
            }
            else{
                val restaurant = viewModel.getRestaurant(email, password)

                if(restaurant?.email == email && restaurant.password == password){
                    Toast.makeText(activity, "wow, restaurant", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(activity, "Credentials don't match", Toast.LENGTH_SHORT).show()
                    clearEditTexts()
                }
            }

        }
    }

    private fun clearEditTexts(){
        binding.etEmail.text.clear()
        binding.etPassword.text.clear()
    }
}