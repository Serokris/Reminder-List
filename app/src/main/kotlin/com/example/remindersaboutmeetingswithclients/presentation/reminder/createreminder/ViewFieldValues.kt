package com.example.remindersaboutmeetingswithclients.presentation.reminder.createreminder

data class ViewFieldValues(
    val titleText: String,
    val dateText: String,
    val timeText: String,
    val dateSwitcherIsChecked: Boolean,
    val timeSwitcherIsChecked: Boolean,
    val calendarTime: Long
)