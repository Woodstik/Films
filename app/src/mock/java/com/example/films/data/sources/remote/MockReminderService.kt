package com.example.films.data.sources.remote

import android.text.format.DateUtils
import com.example.films.TestData
import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.CreateReminderRequest
import com.example.films.data.requests.DeleteRemindersRequest
import io.reactivex.Completable
import io.reactivex.Single

class MockReminderService(private val movies: TestData.Movies) : ReminderService {

    private val reminders = mutableListOf<MovieReminder>()

    override fun getReminders(): Single<List<MovieReminder>> {
        return Single.just(reminders)
    }

    override fun getTodayReminders(): Single<List<MovieReminder>> {
        return Single.just(reminders.filter { DateUtils.isToday(it.remindDate.time) })
    }

    override fun createReminder(request: CreateReminderRequest): Single<Long> {
        val movie = movies.getById(request.movieId)
        val reminderId = (reminders.size + 1).toLong()
        reminders.add(MovieReminder(reminderId, movie, request.remindDate ?: movie.releaseDate))
        return Single.just(reminderId)
    }

    override fun deleteReminders(request: DeleteRemindersRequest): Completable {
        for(id in request.reminderIds){
            reminders.remove(reminders.find { it.id == id })
        }
        return Completable.complete()
    }

    override fun getReminder(id: Long): Single<MovieReminder> {
        return Single.just(reminders.find { it.id == id }!!)
    }
}