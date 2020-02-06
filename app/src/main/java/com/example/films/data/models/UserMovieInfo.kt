package com.example.films.data.models

import java.util.*

data class UserMovieInfo(
    val watched: Boolean = false,
    val watchedDate: Date? = null
)