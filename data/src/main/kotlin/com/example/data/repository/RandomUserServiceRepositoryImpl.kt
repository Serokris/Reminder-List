package com.example.data.repository

import com.example.data.mappers.toClientList
import com.example.data.source.remote.RandomUserApiService
import com.example.domain.models.Client
import com.example.domain.repository.RandomUserServiceRepository
import javax.inject.Inject

class RandomUserServiceRepositoryImpl @Inject constructor(
    private val apiService: RandomUserApiService
) : RandomUserServiceRepository {

    override suspend fun getRandomClients(count: Int): List<Client> {
        return apiService.getRandomClients(count).toClientList()
    }
}