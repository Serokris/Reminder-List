package com.example.remindersaboutmeetingswithclients.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FullNameResponse(
    @SerializedName("first") val firstName: String,
    @SerializedName("last") val lastName: String,
) : Serializable