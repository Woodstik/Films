package com.example.films.data.sources

import com.example.films.data.models.Movie
import com.example.films.data.sources.remote.MovieService
import io.reactivex.Flowable

class MovieRepository(private val movieService: MovieService) : MovieDataSource {

    override fun getNewReleases(): Flowable<List<Movie>> {
        return Flowable.just(movieService.newReleases())
    }

    override fun getPopularMovies(): Flowable<List<Movie>> {
        return Flowable.just(movieService.popularMovies())
    }

    override fun getUpcomingMovies(): Flowable<List<Movie>> {
        return Flowable.just(movieService.upcomingMovies())
    }
}
