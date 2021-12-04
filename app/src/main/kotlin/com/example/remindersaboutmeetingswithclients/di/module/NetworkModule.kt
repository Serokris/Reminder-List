package com.example.remindersaboutmeetingswithclients.di.module

import com.example.data.source.remote.RandomUserApiService
import com.example.remindersaboutmeetingswithclients.di.common.BaseUrl
import com.example.remindersaboutmeetingswithclients.utils.constants.NetworkConstants.RANDOM_USER_SERVICE_BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideRandomUserApiService(
        @BaseUrl baseUrl: String,
        coroutineCallAdapterFactory: CoroutineCallAdapterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): RandomUserApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(coroutineCallAdapterFactory)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(RandomUserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCoroutineCallAdapterFactory(): CoroutineCallAdapterFactory {
        return CoroutineCallAdapterFactory.invoke()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    @BaseUrl
    fun provideRandomUserApiServiceBaseUrl(): String {
        return RANDOM_USER_SERVICE_BASE_URL
    }
}