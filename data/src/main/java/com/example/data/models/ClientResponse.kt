package com.example.data.models

import android.os.Parcelable
import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ClientResponse(
    val email: String,
    @Embedded @SerializedName("name") val fullName: @RawValue FullNameResponse,
    @Embedded val picture: @RawValue PictureResponse,
) : Parcelable