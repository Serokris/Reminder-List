package com.example.domain.interactors

import com.example.domain.models.ReminderItem
import com.example.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderInteractor @Inject constructor(private val reminderRepository: ReminderRepository) {

    suspend fun insert(reminderItem: ReminderItem) = reminderRepository.insert(reminderItem)

    suspend fun delete(reminderItem: ReminderItem) = reminderRepository.delete(reminderItem)

    suspend fun deleteAllReminder() = reminderRepository.deleteAllReminder()

    fun getAllReminders(): Flow<List<ReminderItem>> = reminderRepository.getAllReminders()
}