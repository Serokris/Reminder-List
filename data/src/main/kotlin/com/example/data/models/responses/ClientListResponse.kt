package com.example.data.models.responses

import com.google.gson.annotations.SerializedName

data class ClientListResponse(
    @SerializedName("results") val clients: List<ClientResponse>
)