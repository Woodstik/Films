package com.example.films.data.models

import java.util.*

data class UserMovieInfo(
    var watched: Boolean = false,
    var watchedDate: Date? = null
)