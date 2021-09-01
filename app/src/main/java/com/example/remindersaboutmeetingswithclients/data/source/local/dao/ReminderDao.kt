package com.example.remindersaboutmeetingswithclients.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.remindersaboutmeetingswithclients.domain.models.ReminderItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert
    suspend fun insert(reminderItem: ReminderItem)

    @Delete
    suspend fun delete(reminderItem: ReminderItem)

    @Query("DELETE FROM `reminder-table`")
    suspend fun deleteAllReminders()

    @Query("SELECT * FROM `reminder-table`")
    fun getAllReminders(): Flow<List<ReminderItem>>
}