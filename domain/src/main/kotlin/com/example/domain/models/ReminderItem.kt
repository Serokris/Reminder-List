package com.example.domain.models

data class ReminderItem(
    val id: Int,
    val title: String,
    val date: String,
    val time: String,
    val client: Client,
    var requestCode: Int = 0
)