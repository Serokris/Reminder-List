package com.example.remindersaboutmeetingswithclients.di

import com.example.remindersaboutmeetingswithclients.data.repository.ReminderRepositoryImpl
import com.example.remindersaboutmeetingswithclients.data.source.local.dao.ReminderDao
import com.example.remindersaboutmeetingswithclients.data.source.remote.RandomUserApiService
import com.example.remindersaboutmeetingswithclients.domain.repository.ReminderRepository
import com.example.remindersaboutmeetingswithclients.domain.usecases.ReminderUseCase
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
        randomUserApiService: RandomUserApiService
    ) : ReminderRepository {
        return ReminderRepositoryImpl(reminderDao, randomUserApiService)
    }

    @Singleton
    @Provides
    fun provideReminderUseCase(reminderRepository: ReminderRepository) : ReminderUseCase {
        return ReminderUseCase(reminderRepository)
    }
}