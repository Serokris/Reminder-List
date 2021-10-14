package com.example.remindersaboutmeetingswithclients.data.mappers

import com.example.remindersaboutmeetingswithclients.data.models.ClientResponse
import com.example.remindersaboutmeetingswithclients.data.models.FullNameResponse
import com.example.remindersaboutmeetingswithclients.data.models.PictureResponse
import com.example.remindersaboutmeetingswithclients.data.models.ReminderItemEntity
import com.example.remindersaboutmeetingswithclients.domain.models.Client
import com.example.remindersaboutmeetingswithclients.domain.models.FullName
import com.example.remindersaboutmeetingswithclients.domain.models.Picture
import com.example.remindersaboutmeetingswithclients.domain.models.ReminderItem

fun ReminderItem.toReminderItemEntity(): ReminderItemEntity {
    return ReminderItemEntity(
        id = this.id,
        title = this.title,
        date = this.date,
        time = this.time,
        ClientResponse(
            client.email,
            FullNameResponse(client.fullName.firstName, client.fullName.lastName),
            PictureResponse(client.picture.large)
        )
    )
}

fun ReminderItemEntity.toReminderItem(): ReminderItem {
    return ReminderItem(
        id = this.id,
        title = this.title,
        date = this.date,
        time = this.time,
        Client(
            client.email,
            FullName(client.fullName.firstName, client.fullName.lastName),
            Picture(client.picture.large)
        )
    )
}