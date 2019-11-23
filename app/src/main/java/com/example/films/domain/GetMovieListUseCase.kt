package com.example.films.domain

import com.example.films.data.models.MovieList
import com.example.films.data.sources.MovieListDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class GetMovieListUseCase(
    private val movieListDataSource: MovieListDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<Long, MovieList>(schedulerIO, schedulerMainThread) {

    override fun onCreate(parameter: Long?): Flowable<MovieList> {
        return movieListDataSource.getMovieList(parameter!!)
    }
}