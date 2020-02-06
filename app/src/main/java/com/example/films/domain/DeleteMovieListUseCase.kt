package com.example.films.domain

import com.example.films.data.sources.MovieListDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class DeleteMovieListUseCase(
    private val movieListDataSource: MovieListDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<Long, Unit>(schedulerIO, schedulerMainThread) {

    override fun onCreate(parameter: Long?): Flowable<Unit> {
        return movieListDataSource.deleteMovieList(parameter!!)
            .andThen(Flowable.just(Unit))
    }
}