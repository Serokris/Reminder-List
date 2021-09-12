package com.example.remindersaboutmeetingswithclients.domain.repository

import com.example.remindersaboutmeetingswithclients.domain.models.response.ClientListResponse
import retrofit2.Response

interface RandomUserServiceRepository {
    suspend fun getRandomClients(count: Int): Response<ClientListResponse>
}