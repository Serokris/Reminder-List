package com.example.remindersaboutmeetingswithclients.data.network

import com.example.remindersaboutmeetingswithclients.data.network.response.ClientResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://randomuser.me/"

interface RandomUserApiService {
    @GET("api")
    suspend fun getRandomUsers(@Query("results") count: Int) : ClientResponse
}
