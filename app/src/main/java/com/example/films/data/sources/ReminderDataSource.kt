package com.example.films.data.sources

import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.CreateReminderRequest
import com.example.films.data.requests.DeleteRemindersRequest
import com.example.films.utils.Optional
import io.reactivex.Completable
import io.reactivex.Flowable

interface ReminderDataSource {
    fun createReminder(request: CreateReminderRequest): Completable
    fun deleteReminders(request: DeleteRemindersRequest): Completable
    fun getNextReminder(): Flowable<Optional<MovieReminder>>
    fun getReminders(forceRefresh: Boolean = true): Flowable<List<MovieReminder>>
    fun getTodayReminders(): Flowable<List<MovieReminder>>
}