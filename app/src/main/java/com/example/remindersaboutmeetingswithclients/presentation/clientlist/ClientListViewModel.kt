package com.example.remindersaboutmeetingswithclients.presentation.clientlist

import androidx.lifecycle.ViewModel
import com.example.domain.interactors.RandomUserServiceInteractor
import com.example.domain.models.Client
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClientListViewModel @Inject constructor(
    private val randomUserServiceInteractor: RandomUserServiceInteractor
) : ViewModel() {
    suspend fun getClientList(count: Int): List<Client> {
        return randomUserServiceInteractor.getRandomClients(count)
    }
}