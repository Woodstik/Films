package com.example.films.domain

import com.example.films.data.sources.MovieListDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class CreateMovieListUseCase (
    private val movieListDataSource: MovieListDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<String, Int>(schedulerIO, schedulerMainThread) {

    override fun onCreate(parameter: String?): Flowable<Int> {
        return movieListDataSource.createList(parameter!!)
    }
}