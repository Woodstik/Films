package com.example.films.domain

import com.example.films.data.requests.CreateReminderRequest
import com.example.films.data.sources.MovieListDataSource
import com.example.films.data.sources.ReminderDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class CreateReminderUseCase(
    private val reminderDataSource: ReminderDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<CreateReminderRequest, Unit>(schedulerIO, schedulerMainThread) {

    override fun onCreate(parameter: CreateReminderRequest?): Flowable<Unit> {
        return reminderDataSource.createReminder(parameter!!)
            .andThen(Flowable.just(Unit))
    }
}