package com.example.remindersaboutmeetingswithclients.repository

import com.example.remindersaboutmeetingswithclients.data.db.dao.ReminderDao
import com.example.remindersaboutmeetingswithclients.data.db.entity.ReminderItem
import com.example.remindersaboutmeetingswithclients.data.network.RandomUserApiService
import javax.inject.Inject

class ReminderRepository @Inject constructor(
    private val reminderDao: ReminderDao,
    private val apiService: RandomUserApiService
) {
    suspend fun insert(reminderItem: ReminderItem) = reminderDao.insert(reminderItem)

    suspend fun delete(reminderItem: ReminderItem) = reminderDao.delete(reminderItem)

    suspend fun deleteAllReminder() = reminderDao.deleteAllReminders()

    fun getAllReminders() = reminderDao.getAllReminders()

    suspend fun getRandomUsers(count: Int) = apiService.getRandomUsers(count)
}