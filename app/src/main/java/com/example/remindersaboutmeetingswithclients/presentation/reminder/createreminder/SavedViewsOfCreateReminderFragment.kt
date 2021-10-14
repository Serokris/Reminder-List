package com.example.remindersaboutmeetingswithclients.presentation.reminder.createreminder

data class SavedViewsOfCreateReminderFragment(
    val titleEditText: String,
    val selectedDateText: String,
    val selectedTimeText: String,
    val checkedInstalledDateSwitch: Boolean,
    val checkedInstalledTimeSwitch: Boolean,
    val calendarTime: Long
)