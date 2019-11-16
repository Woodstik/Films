package com.example.films.domain

import com.example.films.data.models.MovieReminder
import com.example.films.data.sources.MovieListDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class GetTodayRemindersUseCase(
    private val movieListDataSource: MovieListDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<Unit, List<MovieReminder>>(schedulerIO, schedulerMainThread) {

    override fun onCreate(parameter: Unit?): Flowable<List<MovieReminder>> {
        return movieListDataSource.getTodayReminders()
    }
}