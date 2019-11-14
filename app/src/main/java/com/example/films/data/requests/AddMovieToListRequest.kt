package com.example.films.data.requests

data class AddMovieToListRequest(
    val movieId: Int,
    val listId: Long
)