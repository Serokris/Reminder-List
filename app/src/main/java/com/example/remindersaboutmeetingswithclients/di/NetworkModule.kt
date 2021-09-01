package com.example.remindersaboutmeetingswithclients.di

import com.example.remindersaboutmeetingswithclients.data.source.remote.RandomUserApiService
import com.example.remindersaboutmeetingswithclients.utils.Constants.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideRandomUserApiService(): RandomUserApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RandomUserApiService::class.java)
    }
}