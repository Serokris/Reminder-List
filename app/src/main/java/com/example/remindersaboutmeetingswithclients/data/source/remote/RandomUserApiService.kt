package com.example.remindersaboutmeetingswithclients.data.source.remote

import com.example.remindersaboutmeetingswithclients.domain.models.response.ClientResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val RANDOM_USER_SERVICE_BASE_URL = "https://randomuser.me/"

interface RandomUserApiService {
    @GET("api")
    suspend fun getRandomUsers(@Query("results") count: Int) : Response<ClientResponse>
}