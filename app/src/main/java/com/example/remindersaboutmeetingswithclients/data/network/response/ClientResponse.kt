package com.example.remindersaboutmeetingswithclients.data.network.response

import com.example.remindersaboutmeetingswithclients.data.db.entity.Client
import com.google.gson.annotations.SerializedName

data class ClientResponse(
    @SerializedName("results") val clients: List<Client>
)