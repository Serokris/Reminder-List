package com.example.remindersaboutmeetingswithclients.data.db.entity

import android.os.Parcelable
import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Client(
    val email: String,
    @Embedded @SerializedName("name") val fullName: @RawValue FullName,
    @Embedded val picture: @RawValue Picture,
) : Parcelable