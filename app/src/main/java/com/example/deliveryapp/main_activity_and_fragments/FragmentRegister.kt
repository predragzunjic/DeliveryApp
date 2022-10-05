package com.example.deliveryapp.main_activity_and_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.deliveryapp.R
import com.example.deliveryapp.LoginRegisterViewModel
import com.example.deliveryapp.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentRegister : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: LoginRegisterViewModel by activityViewModels()

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

        return binding.root
    }

    private fun setUpButtonRegisterListener(clientOrRestaurant: String?){
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val repeatedPassword = binding.etRepeatPassword.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(activity, "Please enter correct values", Toast.LENGTH_SHORT).show()
            clearEditTexts()
        }
        else if(password != repeatedPassword){
            Toast.makeText(activity, "Please repeat your password correctly", Toast.LENGTH_SHORT).show()
            clearEditTexts()
        }
        else{
            if(clientOrRestaurant == "client"){
                val client = viewModel.getClient(email, password)

                if(client?.email.equals(email) && client?.password.equals(password)){
                    Toast.makeText(activity, "There is already an existing client with these credentials",
                        Toast.LENGTH_SHORT).show()

                    clearEditTexts()
                }
                else{
                    val bundle = Bundle()
                    bundle.putString("emailKey", email)
                    bundle.putString("passwordLey", password)
                    findNavController().navigate(R.id.action_fragmentRegister_to_fragmentClientData, bundle)
                }
            }
            else{
                val restaurant = viewModel.getRestaurant(email, password)

                if(restaurant?.email == email && restaurant?.password == password){
                    Toast.makeText(activity, "Restaurant with these credentials already exists",
                        Toast.LENGTH_SHORT).show()

                    clearEditTexts()
                }
                else{
                    val bundle = Bundle()
                    bundle.putString("emailKey", email)
                    bundle.putString("passwordLey", password)
                    findNavController().navigate(R.id.action_fragmentRegister_to_fragmentRestaurantData, bundle)
                }
            }
        }
    }

    private fun clearEditTexts(){
        binding.etEmail.text.clear()
        binding.etPassword.text.clear()
        binding.etRepeatPassword.text.clear()
    }
}