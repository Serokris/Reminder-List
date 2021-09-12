package com.example.remindersaboutmeetingswithclients.domain.models.response

import com.example.remindersaboutmeetingswithclients.domain.models.Client
import com.google.gson.annotations.SerializedName

data class ClientListResponse(
    @SerializedName("results") val clients: List<Client>
)