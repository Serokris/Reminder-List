package com.example.remindersaboutmeetingswithclients.domain.models

data class Client(
    val email: String,
    val fullName: FullName,
    val picture: Picture,
)