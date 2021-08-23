package com.example.remindersaboutmeetingswithclients.repository

import com.example.remindersaboutmeetingswithclients.data.db.ReminderDatabase
import com.example.remindersaboutmeetingswithclients.data.db.entity.ReminderItem
import com.example.remindersaboutmeetingswithclients.data.network.RandomUserApiService

class ReminderRepository(
    db: ReminderDatabase,
    private val apiService: RandomUserApiService) {

    private val reminderDao = db.reminderDao()

    suspend fun insert(reminderItem: ReminderItem) = reminderDao.insert(reminderItem)

    suspend fun delete(reminderItem: ReminderItem) = reminderDao.delete(reminderItem)

    suspend fun deleteAllReminder() = reminderDao.deleteAllReminders()

    fun getAllReminders() = reminderDao.getAllReminders()

    suspend fun getRandomUsers(count: Int) = apiService.getRandomUsers(count)
}