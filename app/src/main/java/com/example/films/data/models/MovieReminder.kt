package com.example.films.data.models

import java.util.*

data class MovieReminder(
    val id: Long,
    val movie: Movie,
    val remindDate: Date
)
