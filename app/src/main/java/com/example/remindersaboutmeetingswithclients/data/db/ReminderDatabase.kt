package com.example.remindersaboutmeetingswithclients.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.remindersaboutmeetingswithclients.data.db.dao.ReminderDao
import com.example.remindersaboutmeetingswithclients.data.db.entity.ReminderItem

@Database(entities = [ReminderItem::class], version = 1, exportSchema = false)
abstract class ReminderDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}