package com.example.films.data.models

data class HomeMovies(
    val newReleases: List<Movie>,
    val upcomingMovies: List<Movie>,
    val popularMovies: List<Movie>
)
