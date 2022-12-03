package com.example.deliveryapp.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.repositories.LoginRegisterRepository
import com.example.deliveryapp.tables.Client
import com.example.deliveryapp.tables.Restaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentRegisterViewModel @Inject constructor(
    private val repository: LoginRegisterRepository
): ViewModel() {
    private var client: Client? = null
    private var restaurant: Restaurant? = null

    private var _goToActivityRestaurant = MutableSharedFlow<Boolean>()
    var goToActivityRestaurant = _goToActivityRestaurant.asSharedFlow()

    private var _goToActivityClient = MutableSharedFlow<Boolean>()
    var goToActivityClient = _goToActivityClient.asSharedFlow()

    private var _toastEmptyField = MutableSharedFlow<Boolean>()
    var toastEmptyField = _toastEmptyField.asSharedFlow()

    private var _alreadyInDatabase = MutableSharedFlow<Boolean>()
    var alreadyInDatabase = _alreadyInDatabase.asSharedFlow()

    private var _wrongRepeatedPassword = MutableSharedFlow<Boolean>()
    var wrongRepeatedPassword = _wrongRepeatedPassword.asSharedFlow()

    fun getRestaurant(): Restaurant?{
        return restaurant
    }

    fun getClient(): Client?{
        return client
    }

    private fun checkRestaurant(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val exists = repository.getRestaurant2(email, password)

            if(exists) _alreadyInDatabase.emit(true)
            else{
                restaurant = repository.getRestaurant(email, password)
                _goToActivityRestaurant.emit(true)
            }
        }
    }

    private fun checkClient(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            val exists = repository.getClient2(email, password)

            if(exists) _alreadyInDatabase.emit(true)
            else{
                client = repository.getClient(email, password)
                _goToActivityClient.emit(true)
            }
        }
    }

    fun isInDatabase(email: String, password: String, repeatedPassword: String, clientOrRestaurant: String?){
        if(email.isEmpty() || password.isEmpty() || repeatedPassword.isEmpty()){
            viewModelScope.launch(Dispatchers.Main) {
                Log.d("tag1", " tu")
                _toastEmptyField.emit(true)
            }
        }
        else if(password != repeatedPassword){
            viewModelScope.launch(Dispatchers.Main) {
                _wrongRepeatedPassword.emit(true)
            }
        }
        else{
            if(clientOrRestaurant.equals("client")) checkClient(email, password)
            else checkRestaurant(email, password)
        }
    }
}