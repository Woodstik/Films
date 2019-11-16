package com.example.films.domain

import com.example.films.data.requests.AddMovieToListRequest
import com.example.films.data.sources.MovieListDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class AddMovieToListUseCase (
    private val movieListDataSource: MovieListDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<AddMovieToListRequest, Unit>(schedulerIO, schedulerMainThread) {

    override fun onCreate(parameter: AddMovieToListRequest?): Flowable<Unit> {
        return movieListDataSource.addMovieToList(parameter!!)
            .andThen(Flowable.just(Unit))
    }
}