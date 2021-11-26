package com.example.remindersaboutmeetingswithclients.presentation.clientlist

import androidx.lifecycle.ViewModel
import com.example.domain.common.Result
import com.example.domain.interactors.RandomUserServiceInteractor
import com.example.domain.models.Client
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClientListViewModel @Inject constructor(
    private val randomUserServiceInteractor: RandomUserServiceInteractor
) : ViewModel() {

    fun getClientList(count: Int): Flow<Result<List<Client>>> {
        return randomUserServiceInteractor.getRandomClients(count)
    }
}