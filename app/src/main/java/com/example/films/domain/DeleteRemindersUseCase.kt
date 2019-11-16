package com.example.films.domain

import com.example.films.data.requests.DeleteRemindersRequest
import com.example.films.data.sources.MovieListDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class DeleteRemindersUseCase(
    private val movieListDataSource: MovieListDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<DeleteRemindersRequest,Unit>(schedulerIO, schedulerMainThread) {
    override fun onCreate(parameter: DeleteRemindersRequest?): Flowable<Unit> {
        return movieListDataSource.deleteReminders(parameter!!)
            .andThen(Flowable.just(Unit))
    }
}