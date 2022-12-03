package com.example.deliveryapp.viewmodels

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.activity_restaurant_and_fragments.ActivityRestaurant
import com.example.deliveryapp.repositories.LoginRegisterRepository
import com.example.deliveryapp.tables.Client
import com.example.deliveryapp.tables.Restaurant
import com.example.deliveryapp.tables.Town
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FragmentRestaurantDataViewModel @Inject constructor(
    private val repository: LoginRegisterRepository
): ViewModel() {

    private var restaurant: Restaurant? = null

    private val townsFlow = repository.getTowns()
    val towns : StateFlow<List<String?>>
        get() = townsFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    private var _toastEmptyField = MutableSharedFlow<Boolean>()
    var toastEmptyField = _toastEmptyField.asSharedFlow()

    private var _goToActivityRestaurant = MutableSharedFlow<Boolean>()
    var goToActivityRestaurant = _goToActivityRestaurant.asSharedFlow()

    fun getRestaurantId(): Int?{
        return restaurant?.id_restaurant
    }

    private fun insertRestaurant(restaurant: Restaurant) = CoroutineScope(Dispatchers.IO).launch {
        repository.insertRestaurant(restaurant)
    }

    fun insertRestaurant(email: String?, password: String?, name: String, address: String
        , description: String, phoneNumber: String, image: String, nameTown: String) {

        if (name.isEmpty() || address.isEmpty() || description.isEmpty() || phoneNumber.isEmpty()) {
            viewModelScope.launch {
                _toastEmptyField.emit(true)
            }
        }
        else {
            viewModelScope.launch(Dispatchers.IO) {
                insertRestaurant( Restaurant(name, address, description, phoneNumber, email.toString(),
                    password.toString(), image, nameTown))

                restaurant = repository.getRestaurant(email, password)

                _goToActivityRestaurant.emit(true)
            }
        }
    }
}