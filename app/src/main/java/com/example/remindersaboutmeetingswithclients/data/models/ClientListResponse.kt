package com.example.remindersaboutmeetingswithclients.data.models

import com.google.gson.annotations.SerializedName

data class ClientListResponse(
    @SerializedName("results") val clients: List<ClientResponse>
)