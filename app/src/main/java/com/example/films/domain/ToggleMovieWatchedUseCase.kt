package com.example.films.domain

import com.example.films.data.sources.MovieListDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class ToggleMovieWatchedUseCase (
    private val movieListDataSource: MovieListDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<Int, Unit>(schedulerIO, schedulerMainThread) {

    override fun onCreate(parameter: Int?): Flowable<Unit> {
        return movieListDataSource.toggleMovieWatched(parameter!!)
            .andThen(Flowable.just(Unit))
    }
}