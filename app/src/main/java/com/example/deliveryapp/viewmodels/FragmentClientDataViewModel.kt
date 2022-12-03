package com.example.deliveryapp.viewmodels

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.activity_client_and_fragments.ActivityClient
import com.example.deliveryapp.repositories.LoginRegisterRepository
import com.example.deliveryapp.tables.Client
import com.example.deliveryapp.tables.Town
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FragmentClientDataViewModel @Inject constructor(
    private val repository: LoginRegisterRepository
): ViewModel() {
    private var client: Client? = null

    private val townsFlow = repository.getTowns()
    val towns : StateFlow<List<String?>>
        get() = townsFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    private var _goToActivityClient = MutableSharedFlow<Boolean>()
    var goToActivityClient = _goToActivityClient.asSharedFlow()

    private var _toastEmptyField = MutableSharedFlow<Boolean>()
    var toastEmptyField = _toastEmptyField.asSharedFlow()

    fun getClientId(): Int?{
        return client?.id_client
    }

    fun insertClient(client: Client) = CoroutineScope(Dispatchers.IO).launch {
        repository.insertClient(client)
    }

    fun insertClient(email: String?, password: String?, name: String, surname: String
                                , address: String, nameTown: String){

        if(name.isEmpty() || surname.isEmpty() || address.isEmpty()){
            viewModelScope.launch {
                _toastEmptyField.emit(true)
            }
        }
        else{
            insertClient(Client(name, surname, address, email, password, nameTown))

            viewModelScope.launch {
                client = repository.getClient(email.toString(), password.toString())
                _goToActivityClient.emit(true)
            }
        }
    }
}