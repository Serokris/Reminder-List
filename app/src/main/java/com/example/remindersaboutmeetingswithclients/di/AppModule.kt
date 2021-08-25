package com.example.remindersaboutmeetingswithclients.di

import android.content.Context
import androidx.room.Room
import com.example.remindersaboutmeetingswithclients.data.db.ReminderDatabase
import com.example.remindersaboutmeetingswithclients.data.network.BASE_URL
import com.example.remindersaboutmeetingswithclients.data.network.RandomUserApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context.applicationContext,
        ReminderDatabase::class.java,
        "reminder-list-database"
    ).build()

    @Singleton
    @Provides
    fun provideReminderDao(db: ReminderDatabase) = db.reminderDao()

    @Singleton
    @Provides
    fun provideRandomUserApiService(): RandomUserApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RandomUserApiService::class.java)
}