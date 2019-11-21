package com.example.films.domain

import com.example.films.data.models.MovieReminder
import com.example.films.data.sources.MovieListDataSource
import com.example.films.data.sources.ReminderDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class GetRemindersUseCase(
    private val reminderDataSource: ReminderDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<Unit, List<MovieReminder>>(schedulerIO, schedulerMainThread) {

    override fun onCreate(parameter: Unit?): Flowable<List<MovieReminder>> {
        return reminderDataSource.getReminders()
    }
}