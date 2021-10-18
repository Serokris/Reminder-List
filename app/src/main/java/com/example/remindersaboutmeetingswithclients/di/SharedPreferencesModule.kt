package com.example.remindersaboutmeetingswithclients.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.remindersaboutmeetingswithclients.utils.constants.CreateReminderFragmentConstants.CREATE_REMINDER_FRAGMENT_PREF_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {
    @Singleton
    @Provides
    fun provideSharedPreferencesForCreateReminderFragment(
        @ApplicationContext context: Context,
        @Named("CreateReminderFragmentPreferencesName") preferencesName: String,
        mode: Int
    ): SharedPreferences {
        return context.getSharedPreferences(preferencesName, mode)
    }

    @Singleton
    @Provides
    fun provideEditorForCreateReminderFragmentPreferences(
        sharedPreferences: SharedPreferences
    ): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

    @Singleton
    @Provides
    @Named("CreateReminderFragmentPreferencesName")
    fun provideCreateReminderFragmentPreferencesName(): String {
        return CREATE_REMINDER_FRAGMENT_PREF_NAME
    }

    @Singleton
    @Provides
    fun providePrivateModeForPreferences(): Int {
        return MODE_PRIVATE
    }
}