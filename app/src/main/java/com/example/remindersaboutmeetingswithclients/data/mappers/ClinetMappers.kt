package com.example.remindersaboutmeetingswithclients.data.mappers

import com.example.remindersaboutmeetingswithclients.data.models.ClientListResponse
import com.example.remindersaboutmeetingswithclients.data.models.ClientResponse
import com.example.remindersaboutmeetingswithclients.data.models.FullNameResponse
import com.example.remindersaboutmeetingswithclients.data.models.PictureResponse
import com.example.remindersaboutmeetingswithclients.domain.models.Client
import com.example.remindersaboutmeetingswithclients.domain.models.ClientList
import com.example.remindersaboutmeetingswithclients.domain.models.FullName
import com.example.remindersaboutmeetingswithclients.domain.models.Picture

fun ClientListResponse.toClientList(): List<Client> {
    return this.clients.map { clientResponse -> clientResponse.toClient() }
}

fun ClientResponse.toClient(): Client {
    return Client(
        this.email,
        FullName(this.fullName.firstName, this.fullName.lastName),
        Picture(this.picture.large)
    )
}

fun Client.toClientResponse(): ClientResponse {
    return ClientResponse(
        this.email,
        FullNameResponse(this.fullName.firstName, this.fullName.lastName),
        PictureResponse(this.picture.large)
    )
}