package com.example.domain.repository

import com.example.domain.models.Client

interface RandomUserServiceRepository {
    suspend fun getRandomClients(count: Int): List<Client>
}