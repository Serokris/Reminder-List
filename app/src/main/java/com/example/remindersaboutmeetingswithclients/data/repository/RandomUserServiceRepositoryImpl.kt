package com.example.remindersaboutmeetingswithclients.data.repository

import com.example.remindersaboutmeetingswithclients.data.source.remote.RandomUserApiService
import com.example.remindersaboutmeetingswithclients.domain.models.response.ClientListResponse
import com.example.remindersaboutmeetingswithclients.domain.repository.RandomUserServiceRepository
import retrofit2.Response

class RandomUserServiceRepositoryImpl(
    private val apiService: RandomUserApiService
) : RandomUserServiceRepository {
    override suspend fun getRandomClients(count: Int): Response<ClientListResponse> {
        return apiService.getRandomClients(count)
    }
}