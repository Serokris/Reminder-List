package com.example.remindersaboutmeetingswithclients.di.module

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.remindersaboutmeetingswithclients.di.common.CreateReminderPrefName
import com.example.remindersaboutmeetingswithclients.presentation.reminder.createreminder.CreateReminderFragmentConstants.CREATE_REMINDER_FRAGMENT_PREF_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object SharedPreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferencesForCreateReminderFragment(
        context: Context,
        @CreateReminderPrefName preferencesName: String,
        mode: Int
    ): SharedPreferences {
        return context.getSharedPreferences(preferencesName, mode)
    }

    @Provides
    @Singleton
    fun provideEditorForCreateReminderFragmentPreferences(
        sharedPreferences: SharedPreferences
    ): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

    @Provides
    @Singleton
    @CreateReminderPrefName
    fun provideCreateReminderFragmentPreferencesName(): String {
        return CREATE_REMINDER_FRAGMENT_PREF_NAME
    }

    @Provides
    @Singleton
    fun providePrivateModeForPreferences(): Int {
        return MODE_PRIVATE
    }
}