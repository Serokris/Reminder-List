package com.example.remindersaboutmeetingswithclients.data.db.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FullName(
    @SerializedName("first") val firstName: String,
    @SerializedName("last") val lastName: String,
) : Serializable