package com.example.remindersaboutmeetingswithclients.domain.repository

import com.example.remindersaboutmeetingswithclients.domain.models.ReminderItem
import com.example.remindersaboutmeetingswithclients.domain.models.response.ClientResponse
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    suspend fun insert(reminderItem: ReminderItem)

    suspend fun delete(reminderItem: ReminderItem)

    suspend fun deleteAllReminder()

    suspend fun getRandomUsers(count: Int) : ClientResponse

    fun getAllReminders(): Flow<List<ReminderItem>>
}