package com.example.films.domain

import com.example.films.data.requests.EditListRequest
import com.example.films.data.sources.MovieListDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class EditListUseCase(
    private val movieListDataSource: MovieListDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<EditListRequest, Unit>(schedulerIO, schedulerMainThread) {

    override fun onCreate(parameter: EditListRequest?): Flowable<Unit> {
        return movieListDataSource.editMovieList(parameter!!)
            .andThen(Flowable.just(Unit))
    }
}