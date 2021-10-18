package com.example.domain.interactors

import com.example.domain.models.Client
import com.example.domain.repository.RandomUserServiceRepository
import javax.inject.Inject

class RandomUserServiceInteractor @Inject constructor(
    private val randomUserServiceRepository: RandomUserServiceRepository
) {
    suspend fun getRandomClients(count: Int): List<Client> {
        return randomUserServiceRepository.getRandomClients(count)
    }
}