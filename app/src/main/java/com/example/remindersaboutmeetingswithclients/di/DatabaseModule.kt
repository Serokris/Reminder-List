package com.example.remindersaboutmeetingswithclients.di

import android.content.Context
import androidx.room.Room
import com.example.remindersaboutmeetingswithclients.utils.constants.DatabaseConstants.REMINDER_LIST_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context.applicationContext,
        com.example.data.source.local.ReminderDatabase::class.java,
        REMINDER_LIST_DB_NAME
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideReminderDao(db: com.example.data.source.local.ReminderDatabase) = db.reminderDao()
}