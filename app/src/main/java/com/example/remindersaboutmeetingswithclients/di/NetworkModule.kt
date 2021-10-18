package com.example.remindersaboutmeetingswithclients.di

import com.example.data.source.remote.RandomUserApiService
import com.example.remindersaboutmeetingswithclients.utils.constants.NetworkConstants.RANDOM_USER_SERVICE_BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideRandomUserApiService(
        @Named("RandomUserApiServiceBaseUrl") baseUrl: String,
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

    @Singleton
    @Provides
    fun provideCoroutineCallAdapterFactory(): CoroutineCallAdapterFactory {
        return CoroutineCallAdapterFactory.invoke()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    @Named("RandomUserApiServiceBaseUrl")
    fun provideRandomUserApiServiceBaseUrl(): String {
        return RANDOM_USER_SERVICE_BASE_URL
    }
}