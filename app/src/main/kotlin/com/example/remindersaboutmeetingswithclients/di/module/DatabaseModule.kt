package com.example.remindersaboutmeetingswithclients.di.module

import android.content.Context
import androidx.room.Room
import com.example.data.source.local.ReminderDatabase.Companion.REMINDER_LIST_DB_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context) = Room.databaseBuilder(
        context.applicationContext,
        com.example.data.source.local.ReminderDatabase::class.java,
        REMINDER_LIST_DB_NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideReminderDao(db: com.example.data.source.local.ReminderDatabase) = db.reminderDao()
}