package com.example.films.data.sources

import com.example.films.data.models.Movie
import io.reactivex.Flowable

interface MovieDataSource {
    fun getNewReleases(): Flowable<List<Movie>>
    fun getUpcomingMovies(): Flowable<List<Movie>>
}
