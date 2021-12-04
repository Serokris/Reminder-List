package com.example.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.data.models.db_entities.ReminderItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert
    suspend fun insert(reminderItemEntity: ReminderItemEntity)

    @Delete
    suspend fun delete(reminderItemEntity: ReminderItemEntity)

    @Query("DELETE FROM `reminder-table`")
    suspend fun deleteAllReminders()

    @Query("SELECT * FROM `reminder-table`")
    fun getAllReminders(): Flow<List<ReminderItemEntity>>
}