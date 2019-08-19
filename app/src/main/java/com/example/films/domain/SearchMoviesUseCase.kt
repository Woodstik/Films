package com.example.films.domain

import com.example.films.data.models.Movie
import com.example.films.data.sources.MovieDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class SearchMoviesUseCase(
    private val movieDataSource: MovieDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<String, List<Movie>>(schedulerIO, schedulerMainThread) {
    override fun onCreate(parameter: String?): Flowable<List<Movie>> {
        return movieDataSource.search(parameter!!)
    }
}
