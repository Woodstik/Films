package com.example.films.data.models

import java.util.*

data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val releaseDate: Date,
    val rating: Double,
    val poster: String,
    val backdrop: String,
    val playtime: Int,
    val cast: List<String> = emptyList(),
    val trailerUrl : String = "",
    val userMovieInfo: UserMovieInfo = UserMovieInfo()
)
