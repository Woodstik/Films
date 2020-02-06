package com.example.films.domain

import com.example.films.data.requests.DeleteMovieFromListRequest
import com.example.films.data.sources.MovieListDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class DeleteMovieFromListUseCase(
    private val movieListDataSource: MovieListDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<DeleteMovieFromListRequest, Unit>(schedulerIO, schedulerMainThread) {

    override fun onCreate(parameter: DeleteMovieFromListRequest?): Flowable<Unit> {
        return movieListDataSource.deleteMovieFromList(parameter!!)
            .andThen(Flowable.just(Unit))
    }
}