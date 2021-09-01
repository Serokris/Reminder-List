package com.example.remindersaboutmeetingswithclients.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.remindersaboutmeetingswithclients.data.source.local.dao.ReminderDao
import com.example.remindersaboutmeetingswithclients.domain.models.ReminderItem

@Database(entities = [ReminderItem::class], version = 1, exportSchema = false)
abstract class ReminderDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}