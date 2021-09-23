package com.example.remindersaboutmeetingswithclients.presentation.reminder.createreminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindersaboutmeetingswithclients.domain.interactors.ReminderInteractor
import com.example.remindersaboutmeetingswithclients.domain.models.ReminderItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateReminderViewModel @Inject constructor(
    private val reminderInteractor: ReminderInteractor
) : ViewModel() {
    fun insert(reminderItem: ReminderItem) {
        viewModelScope.launch(Dispatchers.IO) { reminderInteractor.insert(reminderItem) }
    }
}