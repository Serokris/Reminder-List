package com.example.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.models.db_entities.ReminderItemEntity
import com.example.data.source.local.dao.ReminderDao

@Database(entities = [ReminderItemEntity::class], version = 1, exportSchema = false)
abstract class ReminderDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao

    companion object {
        const val REMINDER_LIST_DB_NAME = "reminder-list-database"
    }
}