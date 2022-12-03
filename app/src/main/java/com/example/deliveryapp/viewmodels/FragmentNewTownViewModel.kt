package com.example.deliveryapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.repositories.LoginRegisterRepository
import com.example.deliveryapp.tables.Town
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentNewTownViewModel @Inject constructor(
    private val repository: LoginRegisterRepository
): ViewModel() {
    private val townsFlow = repository.getTowns()
    val towns : StateFlow<List<String?>>
        get() = townsFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    private var _toastAlreadyInDatabase = MutableSharedFlow<Boolean>()
    var toastAlreadyInDatabase = _toastAlreadyInDatabase.asSharedFlow()

    private var _insertedInDatabase = MutableSharedFlow<Boolean>()
    var insertedInDatabase = _insertedInDatabase.asSharedFlow()


    fun insertTown(townName: String){
        viewModelScope.launch(Dispatchers.IO) {
            val exists = repository.townInDatabase(townName)

            if(exists) _toastAlreadyInDatabase.emit(true)
            else{
                repository.insertTown(Town(townName))
                _insertedInDatabase.emit(true)
            }
        }
    }
}