package com.example.remindersaboutmeetingswithclients.di

import com.example.remindersaboutmeetingswithclients.data.repository.RandomUserServiceRepositoryImpl
import com.example.remindersaboutmeetingswithclients.data.repository.ReminderRepositoryImpl
import com.example.remindersaboutmeetingswithclients.data.source.local.dao.ReminderDao
import com.example.remindersaboutmeetingswithclients.data.source.remote.RandomUserApiService
import com.example.remindersaboutmeetingswithclients.domain.interactors.RandomUserServiceInteractor
import com.example.remindersaboutmeetingswithclients.domain.repository.ReminderRepository
import com.example.remindersaboutmeetingswithclients.domain.interactors.ReminderInteractor
import com.example.remindersaboutmeetingswithclients.domain.repository.RandomUserServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Singleton
    @Provides
    fun provideReminderRepository(
        reminderDao: ReminderDao,
    ): ReminderRepository {
        return ReminderRepositoryImpl(reminderDao)
    }

    @Singleton
    @Provides
    fun provideReminderInteractor(reminderRepository: ReminderRepository): ReminderInteractor {
        return ReminderInteractor(reminderRepository)
    }

    @Singleton
    @Provides
    fun provideRandomUserServiceRepository(
        randomUserApiService: RandomUserApiService
    ): RandomUserServiceRepository {
        return RandomUserServiceRepositoryImpl(randomUserApiService)
    }

    @Singleton
    @Provides
    fun provideRandomUserServiceInteractor(
        randomUserServiceRepository: RandomUserServiceRepository
    ): RandomUserServiceInteractor{
        return RandomUserServiceInteractor(randomUserServiceRepository)
    }
}