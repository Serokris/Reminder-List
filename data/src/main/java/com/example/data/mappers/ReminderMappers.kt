package com.example.data.mappers

import com.example.data.models.responses.ClientResponse
import com.example.data.models.responses.FullNameResponse
import com.example.data.models.responses.PictureResponse
import com.example.data.models.db_entities.ReminderItemEntity
import com.example.domain.models.Client
import com.example.domain.models.FullName
import com.example.domain.models.Picture
import com.example.domain.models.ReminderItem

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
        ),
        requestCode = this.requestCode
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
        ),
        requestCode = this.requestCode
    )
}