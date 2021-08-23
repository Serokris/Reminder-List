package com.example.remindersaboutmeetingswithclients.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindersaboutmeetingswithclients.data.db.ReminderDatabase
import com.example.remindersaboutmeetingswithclients.data.db.entity.ReminderItem
import com.example.remindersaboutmeetingswithclients.data.network.RandomUserApiService
import com.example.remindersaboutmeetingswithclients.repository.ReminderRepository
import kotlinx.coroutines.launch

class ReminderViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ReminderRepository(ReminderDatabase.getDataBase(application), RandomUserApiService())

    fun insert(reminderItem: ReminderItem) = viewModelScope.launch { repository.insert(reminderItem) }

    fun delete(reminderItem: ReminderItem) = viewModelScope.launch { repository.delete(reminderItem) }

    fun deleteAllReminders() = viewModelScope.launch { repository.deleteAllReminder() }

    fun getAllReminders() = repository.getAllReminders()

    suspend fun getRandomUsers(count: Int) = repository.getRandomUsers(count)
}