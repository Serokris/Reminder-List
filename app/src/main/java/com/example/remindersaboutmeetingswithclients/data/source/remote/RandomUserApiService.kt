package com.example.remindersaboutmeetingswithclients.data.source.remote

import com.example.remindersaboutmeetingswithclients.data.models.ClientListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApiService {
    @GET("api")
    suspend fun getRandomClients(@Query("results") count: Int) : Response<ClientListResponse>
}