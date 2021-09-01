package com.example.remindersaboutmeetingswithclients.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.remindersaboutmeetingswithclients.domain.models.ReminderItem
import com.example.remindersaboutmeetingswithclients.domain.models.response.ClientResponse
import com.example.remindersaboutmeetingswithclients.domain.usecases.ReminderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val reminderUseCase: ReminderUseCase
) : ViewModel() {
    fun insert(reminderItem: ReminderItem) {
        viewModelScope.launch { reminderUseCase.insert(reminderItem) }
    }

    fun delete(reminderItem: ReminderItem) {
        viewModelScope.launch { reminderUseCase.delete(reminderItem) }
    }

    fun deleteAllReminders() {
        viewModelScope.launch { reminderUseCase.deleteAllReminder() }
    }

    fun getAllReminders() : LiveData<List<ReminderItem>> {
        return reminderUseCase.getAllReminders().asLiveData()
    }

    suspend fun getRandomUsers(count: Int) : ClientResponse {
        return reminderUseCase.getRandomUsers(count)
    }
}