package com.example.films.data.sources.remote

import com.example.films.data.models.Movie
import io.reactivex.Flowable

interface MovieService {

    fun newReleases() : Flowable<List<Movie>>

    fun upcomingMovies() : Flowable<List<Movie>>

    fun popularMovies() : Flowable<List<Movie>>
}
