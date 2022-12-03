package com.example.deliveryapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.deliveryapp.repositories.LoginRegisterRepository
import com.example.deliveryapp.tables.Client
import com.example.deliveryapp.tables.Restaurant
import com.example.deliveryapp.tables.Town
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
@HiltViewModel
class LoginRegisterViewModel @Inject constructor(
    private val repository: LoginRegisterRepository
) : ViewModel() {
    private var client: Client? = null
    private var restaurant: Restaurant? = null

    private lateinit var towns: LiveData<List<Town>>

    fun insertRestaurant(restaurant: Restaurant) {
        runBlocking {
            val job = launch {
                repository.insertRestaurant(restaurant)
            }

            job.join()
        }
    }

    fun insertClient(client: Client) = CoroutineScope(Dispatchers.Main).launch {
        repository.insertClient(client)
    }

    fun insertTown(town: Town) = CoroutineScope(Dispatchers.Main).launch {
        repository.insertTown(town)
    }
/*
    fun getTowns(): LiveData<List<Town>>{
        privateGetTowns()

        return towns
    }

    private fun privateGetTowns(){
        towns = repository.getTowns().asLiveData()
    }
*/
    fun deleteTown(town: String) = CoroutineScope(Dispatchers.Main).launch {
        repository.deleteTown(town)
    }


    fun getClient(email: String, password: String): Client?{
        runBlocking {
            val job = launch {
                client = repository.getClient(email, password)
            }
            job.join()
        }

        return client
    }

    fun getRestaurant(email: String?, password: String?): Restaurant?{
        runBlocking {
            val job = launch {
                restaurant = repository.getRestaurant(email, password)
            }

            job.join()
        }

        return restaurant
    }

    fun deleteALlRestaurants() = CoroutineScope(Dispatchers.Main).launch{
        repository.deleteAllRestaurants()
    }
}