package com.example.remindersaboutmeetingswithclients.presentation.reminder.reminderlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.remindersaboutmeetingswithclients.domain.interactors.ReminderInteractor
import com.example.remindersaboutmeetingswithclients.domain.models.ReminderItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderListViewModel @Inject constructor(
    private val reminderInteractor: ReminderInteractor
) : ViewModel() {
    fun insert(reminderItem: ReminderItem) {
        viewModelScope.launch(Dispatchers.IO) { reminderInteractor.insert(reminderItem) }
    }

    fun delete(reminderItem: ReminderItem) {
        viewModelScope.launch(Dispatchers.IO) { reminderInteractor.delete(reminderItem) }
    }

    fun deleteAllReminders() {
        viewModelScope.launch(Dispatchers.IO) { reminderInteractor.deleteAllReminder() }
    }

    fun getAllReminders(): LiveData<List<ReminderItem>> {
        return reminderInteractor.getAllReminders().asLiveData()
    }
}