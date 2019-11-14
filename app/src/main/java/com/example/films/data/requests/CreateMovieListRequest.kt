package com.example.films.data.requests

import com.example.films.utils.randomListColor

data class CreateMovieListRequest(
    val title: String,
    val color: String = randomListColor()
)