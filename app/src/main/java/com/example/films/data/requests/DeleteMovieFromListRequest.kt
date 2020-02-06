package com.example.films.data.requests

data class DeleteMovieFromListRequest(
    val listId: Long,
    val movieId: Int
)