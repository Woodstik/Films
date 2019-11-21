package com.example.films.domain

import com.example.films.data.requests.DeleteRemindersRequest
import com.example.films.data.sources.MovieListDataSource
import com.example.films.data.sources.ReminderDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class DeleteRemindersUseCase(
    private val reminderDataSource: ReminderDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<DeleteRemindersRequest,Unit>(schedulerIO, schedulerMainThread) {
    override fun onCreate(parameter: DeleteRemindersRequest?): Flowable<Unit> {
        return reminderDataSource.deleteReminders(parameter!!)
            .andThen(Flowable.just(Unit))
    }
}