package com.example.remindersaboutmeetingswithclients.data.repository

import com.example.remindersaboutmeetingswithclients.data.source.local.dao.ReminderDao
import com.example.remindersaboutmeetingswithclients.data.source.remote.RandomUserApiService
import com.example.remindersaboutmeetingswithclients.domain.models.ReminderItem
import com.example.remindersaboutmeetingswithclients.domain.models.response.ClientResponse
import com.example.remindersaboutmeetingswithclients.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val reminderDao: ReminderDao,
    private val apiService: RandomUserApiService
) : ReminderRepository {
    override suspend fun insert(reminderItem: ReminderItem) {
        reminderDao.insert(reminderItem)
    }

    override suspend fun delete(reminderItem: ReminderItem) {
        reminderDao.delete(reminderItem)
    }

    override suspend fun deleteAllReminder() {
        reminderDao.deleteAllReminders()
    }

    override suspend fun getRandomUsers(count: Int) : ClientResponse {
        return apiService.getRandomUsers(count)
    }

    override fun getAllReminders(): Flow<List<ReminderItem>> {
        return reminderDao.getAllReminders()
    }
}