package com.example.films.data.models

import java.util.*

data class MovieList(
    val id: Long,
    val title: String,
    val author: String,
    val createdDate: Date,
    val movies: List<Movie>
)
