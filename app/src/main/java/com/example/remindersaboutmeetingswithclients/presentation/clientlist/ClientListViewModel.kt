package com.example.remindersaboutmeetingswithclients.presentation.clientlist

import androidx.lifecycle.ViewModel
import com.example.remindersaboutmeetingswithclients.domain.interactors.RandomUserServiceInteractor
import com.example.remindersaboutmeetingswithclients.domain.models.response.ClientListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ClientListViewModel @Inject constructor(
    private val randomUserServiceInteractor: RandomUserServiceInteractor
) : ViewModel() {
    suspend fun getClientList(count: Int): Response<ClientListResponse> {
        return randomUserServiceInteractor.getRandomClients(count)
    }
}