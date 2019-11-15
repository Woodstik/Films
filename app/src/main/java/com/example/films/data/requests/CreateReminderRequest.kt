package com.example.films.data.requests

import java.util.*

data class CreateReminderRequest(
    val movieId: Int,
    val remindDate: Date? = null
)