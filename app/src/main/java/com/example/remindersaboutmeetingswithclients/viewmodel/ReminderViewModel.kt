package com.example.remindersaboutmeetingswithclients.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindersaboutmeetingswithclients.data.db.entity.ReminderItem
import com.example.remindersaboutmeetingswithclients.repository.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val repository: ReminderRepository
) : ViewModel() {
    fun insert(reminderItem: ReminderItem) = viewModelScope.launch { repository.insert(reminderItem) }

    fun delete(reminderItem: ReminderItem) = viewModelScope.launch { repository.delete(reminderItem) }

    fun deleteAllReminders() = viewModelScope.launch { repository.deleteAllReminder() }

    fun getAllReminders() = repository.getAllReminders()

    suspend fun getRandomUsers(count: Int) = repository.getRandomUsers(count)
}