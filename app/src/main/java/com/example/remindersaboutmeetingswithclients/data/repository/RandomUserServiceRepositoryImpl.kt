package com.example.remindersaboutmeetingswithclients.data.repository

import com.example.remindersaboutmeetingswithclients.data.mappers.toClientList
import com.example.remindersaboutmeetingswithclients.data.source.remote.RandomUserApiService
import com.example.remindersaboutmeetingswithclients.domain.models.Client
import com.example.remindersaboutmeetingswithclients.domain.models.ClientList
import com.example.remindersaboutmeetingswithclients.domain.repository.RandomUserServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RandomUserServiceRepositoryImpl(
    private val apiService: RandomUserApiService
) : RandomUserServiceRepository {

    override suspend fun getRandomClients(count: Int): List<Client> {
        return withContext(Dispatchers.IO) {
            apiService.getRandomClients(count).body()?.toClientList()!!
        }
    }
}