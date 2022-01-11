package com.example.remindersaboutmeetingswithclients.presentation.clientlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Result
import com.example.domain.interactors.RandomUserServiceInteractor
import com.example.domain.models.Client
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ClientListViewModel @Inject constructor(
    private val randomUserServiceInteractor: RandomUserServiceInteractor
) : ViewModel() {

    private val _dataLoading = MutableLiveData(false)
    val dataLoading: LiveData<Boolean> get() = _dataLoading

    private val _clientsList = MutableLiveData<List<Client>>(emptyList())
    val clientsList: LiveData<List<Client>> get() = _clientsList

    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        getClientList()
    }

    private fun getClientList(clientsAmount: Int = 15) {
        randomUserServiceInteractor.getRandomClients(clientsAmount).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _dataLoading.postValue(false)
                    _clientsList.postValue(result.data!!)
                }
                is Result.Loading -> {
                    _dataLoading.postValue(true)
                }
                is Result.Error -> {
                    _dataLoading.postValue(false)
                    _errorMessage.postValue(result.message!!)
                }
            }
        }.launchIn(viewModelScope)
    }
}