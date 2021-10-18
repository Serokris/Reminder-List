package com.example.data.repository

import com.example.data.mappers.toReminderItem
import com.example.data.mappers.toReminderItemEntity
import com.example.data.source.local.dao.ReminderDao
import com.example.domain.models.ReminderItem
import com.example.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val reminderDao: ReminderDao
) : ReminderRepository {

    override suspend fun insert(reminderItem: ReminderItem) {
        reminderDao.insert(reminderItem.toReminderItemEntity())
    }

    override suspend fun delete(reminderItem: ReminderItem) {
        reminderDao.delete(reminderItem.toReminderItemEntity())
    }

    override suspend fun deleteAllReminder() {
        reminderDao.deleteAllReminders()
    }

    override fun getAllReminders(): Flow<List<ReminderItem>> {
        return reminderDao.getAllReminders()
            .map { reminderItemEntityList -> reminderItemEntityList
                .map { reminderItemEntity -> reminderItemEntity.toReminderItem() } }
    }
}