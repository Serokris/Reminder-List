package com.example.remindersaboutmeetingswithclients.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminder-table")
data class ReminderItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val date: String,
    val time: String,
    @Embedded val client: ClientResponse,
    var requestCode: Int = 0
)