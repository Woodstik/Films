package com.example.films.domain

import com.example.films.data.requests.CreateMovieListRequest
import com.example.films.data.sources.MovieListDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class CreateMovieListUseCase (
    private val movieListDataSource: MovieListDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<CreateMovieListRequest, Long>(schedulerIO, schedulerMainThread) {

    override fun onCreate(parameter: CreateMovieListRequest?): Flowable<Long> {
        return movieListDataSource.createList(parameter!!)
    }
}