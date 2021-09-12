package com.example.remindersaboutmeetingswithclients.presentation.reminder

import androidx.lifecycle.*
import com.example.remindersaboutmeetingswithclients.domain.models.ReminderItem
import com.example.remindersaboutmeetingswithclients.domain.interactors.ReminderInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
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