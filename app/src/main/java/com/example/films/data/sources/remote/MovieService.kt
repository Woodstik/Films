package com.example.films.data.sources.remote

import com.example.films.data.models.Movie
import io.reactivex.Single

interface MovieService {

    fun newReleases() : Single<List<Movie>>

    fun upcomingMovies() : Single<List<Movie>>

    fun search(query: String) : Single<List<Movie>>
}
