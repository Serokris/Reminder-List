package com.example.domain.repository

import com.example.domain.models.ReminderItem
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {

    suspend fun insert(reminderItem: ReminderItem)

    suspend fun delete(reminderItem: ReminderItem)

    suspend fun deleteAllReminders()

    fun getAllReminders(): Flow<List<ReminderItem>>
}