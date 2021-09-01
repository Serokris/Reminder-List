package com.example.remindersaboutmeetingswithclients.domain.usecases

import com.example.remindersaboutmeetingswithclients.domain.models.ReminderItem
import com.example.remindersaboutmeetingswithclients.domain.models.response.ClientResponse
import com.example.remindersaboutmeetingswithclients.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderUseCase @Inject constructor(private val reminderRepository: ReminderRepository) {
    suspend fun insert(reminderItem: ReminderItem) = reminderRepository.insert(reminderItem)

    suspend fun delete(reminderItem: ReminderItem) = reminderRepository.delete(reminderItem)

    suspend fun deleteAllReminder() = reminderRepository.deleteAllReminder()

    suspend fun getRandomUsers(count: Int): ClientResponse = reminderRepository.getRandomUsers(count)

    fun getAllReminders(): Flow<List<ReminderItem>> = reminderRepository.getAllReminders()
}