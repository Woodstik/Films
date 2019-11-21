package com.example.films.data.sources.remote

import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.CreateReminderRequest
import com.example.films.data.requests.DeleteRemindersRequest
import io.reactivex.Completable
import io.reactivex.Single

interface ReminderService {
    fun getReminders(): Single<List<MovieReminder>>
    fun getReminder(id: Long) : Single<MovieReminder>
    fun createReminder(request: CreateReminderRequest): Single<Long>
    fun deleteReminders(request: DeleteRemindersRequest): Completable
    fun getTodayReminders(): Single<List<MovieReminder>>
}