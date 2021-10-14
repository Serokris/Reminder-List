package com.example.remindersaboutmeetingswithclients.domain.repository

import com.example.remindersaboutmeetingswithclients.domain.models.Client

interface RandomUserServiceRepository {
    suspend fun getRandomClients(count: Int): List<Client>
}