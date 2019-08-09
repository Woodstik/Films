package com.example.films.data.sources.repositories

import com.example.films.data.models.Movie
import com.example.films.data.sources.MovieDataSource
import com.example.films.data.sources.remote.MovieService
import io.reactivex.Flowable

class MovieRepository(private val movieService: MovieService) : MovieDataSource {

    override fun getNewReleases(): Flowable<List<Movie>> {
        return movieService.newReleases()
            .toFlowable()
    }

    override fun getPopularMovies(): Flowable<List<Movie>> {
        return movieService.popularMovies()
            .toFlowable()
    }

    override fun getUpcomingMovies(): Flowable<List<Movie>> {
        return movieService.upcomingMovies()
            .toFlowable()
    }
}
