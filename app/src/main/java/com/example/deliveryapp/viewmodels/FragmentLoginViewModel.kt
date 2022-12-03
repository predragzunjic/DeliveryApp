package com.example.deliveryapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.repositories.LoginRegisterRepository
import com.example.deliveryapp.tables.Client
import com.example.deliveryapp.tables.Restaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class FragmentLoginViewModel @Inject constructor(
    private val repository: LoginRegisterRepository
): ViewModel() {
    private var client: Client? = null
    private var restaurant: Restaurant? = null

    private var _goToActivityRestaurant = MutableSharedFlow<Boolean>()
    var goToActivityRestaurant = _goToActivityRestaurant.asSharedFlow()

    private var _goToActivityClient = MutableSharedFlow<Boolean>()
    var goToActivityClient = _goToActivityClient.asSharedFlow()

    private var _goToFragmentNewTown = MutableSharedFlow<Boolean>()
    var goToFragmentNewTown = _goToFragmentNewTown.asSharedFlow()

    private var _toastEmptyField = MutableSharedFlow<Boolean>()
    var toastEmptyField = _toastEmptyField.asSharedFlow()

    private var _credentialsNotMatching = MutableSharedFlow<Boolean>()
    var credentialsNotMatching = _credentialsNotMatching.asSharedFlow()


    fun getIdRestaurant(): Int?{
        return restaurant?.id_restaurant
    }

    fun getIdClient(): Int?{
        return client?.id_client
    }

    private fun authenticateRestaurant(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val exists = repository.getRestaurant2(email, password)

            if(exists){
                restaurant = repository.getRestaurant(email, password)
                _goToActivityRestaurant.emit(true)
            }
            else _credentialsNotMatching.emit(true)
        }
    }

    private fun authenticateClient(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            val exists = repository.getClient2(email, password)

            if(exists){
                client = repository.getClient(email, password)
                _goToActivityClient.emit(true)
            }
            else _credentialsNotMatching.emit(true)
        }
    }

    fun authenticate(email: String, password: String, clientOrRestaurant: String?){
        if(email.isEmpty() || password.isEmpty()){
            viewModelScope.launch {
                _toastEmptyField.emit(true)
            }
        }
        else if(email == "AdminAdminovic" && password == "passwordPasswordovic"){
            viewModelScope.launch {
                _goToFragmentNewTown.emit(true)
            }
        }
        else{
            if(clientOrRestaurant.equals("client")) authenticateClient(email, password)
            else authenticateRestaurant(email, password)
        }
    }
}