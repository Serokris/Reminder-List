package com.example.remindersaboutmeetingswithclients.di.module

import com.example.data.repository.RandomUserServiceRepositoryImpl
import com.example.data.repository.ReminderRepositoryImpl
import com.example.domain.repository.RandomUserServiceRepository
import com.example.domain.repository.ReminderRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindReminderRepository(repository: ReminderRepositoryImpl): ReminderRepository

    @Binds
    fun bindRandomUserServiceRepository(
        repository: RandomUserServiceRepositoryImpl
    ): RandomUserServiceRepository
}