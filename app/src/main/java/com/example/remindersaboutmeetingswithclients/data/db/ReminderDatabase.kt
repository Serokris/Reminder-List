package com.example.remindersaboutmeetingswithclients.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.remindersaboutmeetingswithclients.data.db.dao.ReminderDao
import com.example.remindersaboutmeetingswithclients.data.db.entity.ReminderItem

@Database(entities = [ReminderItem::class], version = 1)
abstract class ReminderDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao

    companion object {
        @Volatile
        private var INSTANCE: ReminderDatabase? = null

        fun getDataBase(context: Context): ReminderDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ReminderDatabase::class.java,
                        "reminder-list-database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}