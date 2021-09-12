package com.example.remindersaboutmeetingswithclients.domain.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminder-table")
data class ReminderItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val date: String,
    val time: String,
    @Embedded val client: Client,
    var requestCode: Int = 0
)