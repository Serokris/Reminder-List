package com.example.remindersaboutmeetingswithclients.di

import com.example.data.repository.RandomUserServiceRepositoryImpl
import com.example.data.repository.ReminderRepositoryImpl
import com.example.data.source.local.dao.ReminderDao
import com.example.data.source.remote.RandomUserApiService
import com.example.domain.interactors.RandomUserServiceInteractor
import com.example.domain.repository.ReminderRepository
import com.example.domain.interactors.ReminderInteractor
import com.example.domain.repository.RandomUserServiceRepository
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
    ): RandomUserServiceInteractor {
        return RandomUserServiceInteractor(
            randomUserServiceRepository
        )
    }
}