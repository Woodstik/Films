package com.example.films.domain

import com.example.films.data.requests.RemoveReminderRequest
import com.example.films.data.sources.MovieListDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class RemoveReminderUseCase(
    private val movieListDataSource: MovieListDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<RemoveReminderRequest,Unit>(schedulerIO, schedulerMainThread) {
    override fun onCreate(parameter: RemoveReminderRequest?): Flowable<Unit> {
        return movieListDataSource.removeReminder(parameter!!)
            .andThen(Flowable.just(Unit))
    }
}