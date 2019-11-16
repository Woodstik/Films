package com.example.films.domain

import com.example.films.data.requests.CreateReminderRequest
import com.example.films.data.sources.MovieListDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class CreateReminderUseCase(
    private val movieListDataSource: MovieListDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<CreateReminderRequest, Unit>(schedulerIO, schedulerMainThread) {

    override fun onCreate(parameter: CreateReminderRequest?): Flowable<Unit> {
        return movieListDataSource.createReminder(parameter!!)
            .andThen(Flowable.just(Unit))
    }
}