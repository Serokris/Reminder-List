package com.example.data.mappers

import com.example.data.models.ClientListResponse
import com.example.data.models.ClientResponse
import com.example.data.models.FullNameResponse
import com.example.data.models.PictureResponse
import com.example.domain.models.Client
import com.example.domain.models.FullName
import com.example.domain.models.Picture

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