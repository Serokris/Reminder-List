package com.example.remindersaboutmeetingswithclients.di.module

import com.example.domain.interactors.RandomUserServiceInteractor
import com.example.domain.interactors.ReminderInteractor
import com.example.domain.repository.RandomUserServiceRepository
import com.example.domain.repository.ReminderRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {

    @Provides
    @Singleton
    fun provideReminderInteractor(reminderRepository: ReminderRepository): ReminderInteractor {
        return ReminderInteractor(reminderRepository)
    }

    @Provides
    @Singleton
    fun provideRandomUserServiceInteractor(
        randomUserServiceRepository: RandomUserServiceRepository
    ): RandomUserServiceInteractor {
        return RandomUserServiceInteractor(
            randomUserServiceRepository
        )
    }
}