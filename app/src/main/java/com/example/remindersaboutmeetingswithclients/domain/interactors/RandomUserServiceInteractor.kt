package com.example.remindersaboutmeetingswithclients.domain.interactors

import com.example.remindersaboutmeetingswithclients.domain.models.response.ClientListResponse
import com.example.remindersaboutmeetingswithclients.domain.repository.RandomUserServiceRepository
import retrofit2.Response
import javax.inject.Inject

class RandomUserServiceInteractor @Inject constructor(
    private val randomUserServiceRepository: RandomUserServiceRepository
) {
    suspend fun getRandomClients(count: Int): Response<ClientListResponse> {
        return randomUserServiceRepository.getRandomClients(count)
    }
}