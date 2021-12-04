package com.example.remindersaboutmeetingswithclients.presentation.reminder.createreminder

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.ReminderInteractor
import com.example.domain.models.ReminderItem
import com.example.remindersaboutmeetingswithclients.presentation.reminder.createreminder.CreateReminderFragmentConstants.CALENDAR_TIME
import com.example.remindersaboutmeetingswithclients.presentation.reminder.createreminder.CreateReminderFragmentConstants.DATE_SWITCHER
import com.example.remindersaboutmeetingswithclients.presentation.reminder.createreminder.CreateReminderFragmentConstants.DATE_TEXT
import com.example.remindersaboutmeetingswithclients.presentation.reminder.createreminder.CreateReminderFragmentConstants.TIME_SWITCHER
import com.example.remindersaboutmeetingswithclients.presentation.reminder.createreminder.CreateReminderFragmentConstants.TIME_TEXT
import com.example.remindersaboutmeetingswithclients.presentation.reminder.createreminder.CreateReminderFragmentConstants.TITLE_TEXT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CreateReminderViewModel @Inject constructor(
    private val reminderInteractor: ReminderInteractor,
    private val createReminderFragmentPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
) : ViewModel() {

    fun insert(reminderItem: ReminderItem) {
        viewModelScope.launch(Dispatchers.IO) { reminderInteractor.insert(reminderItem) }
    }

    fun saveViewsState(viewFieldValues: ViewFieldValues) {
        editor.apply {
            putString(TITLE_TEXT, viewFieldValues.titleText)
            putString(DATE_TEXT, viewFieldValues.dateText)
            putString(TIME_TEXT, viewFieldValues.timeText)
            putBoolean(DATE_SWITCHER, viewFieldValues.dateSwitcherIsChecked)
            putBoolean(TIME_SWITCHER, viewFieldValues.timeSwitcherIsChecked)
            putLong(CALENDAR_TIME, viewFieldValues.calendarTime)
        }.apply()
    }

    fun getViewsState(calendar: Calendar): ViewFieldValues {
        createReminderFragmentPreferences.apply {
            return ViewFieldValues(
                getString(TITLE_TEXT, "") ?: "",
                getString(DATE_TEXT, "") ?: "",
                getString(TIME_TEXT, "") ?: "",
                getBoolean(DATE_SWITCHER, false),
                getBoolean(TIME_SWITCHER, false),
                getLong(CALENDAR_TIME, calendar.timeInMillis)
            )
        }
    }
}