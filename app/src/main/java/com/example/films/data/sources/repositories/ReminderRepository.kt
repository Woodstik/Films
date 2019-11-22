package com.example.films.data.sources.repositories

import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.CreateReminderRequest
import com.example.films.data.requests.DeleteRemindersRequest
import com.example.films.data.sources.ReminderDataSource
import com.example.films.data.sources.remote.ReminderService
import com.example.films.utils.Optional
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

class ReminderRepository(private val reminderService: ReminderService) : ReminderDataSource {

    private val remindersSubject = BehaviorSubject.create<List<MovieReminder>>()

    override fun getNextReminder(): Flowable<Optional<MovieReminder>> {
        return getReminders()
            .map {
                return@map if (it.isEmpty()) {
                    Optional<MovieReminder>(null)
                } else {
                    Optional(it.first())
                }
            }
    }

    override fun getReminders(forceRefresh: Boolean): Flowable<List<MovieReminder>> {
        var fetchCompletable = Completable.complete()
        if(forceRefresh){
            fetchCompletable = fetchReminders()
        }
        return fetchCompletable
            .andThen(remindersSubject.toFlowable(BackpressureStrategy.DROP))
    }

    override fun getTodayReminders(): Flowable<List<MovieReminder>> {
        return reminderService.getTodayReminders()
            .toFlowable()
    }

    override fun createReminder(request: CreateReminderRequest): Completable {
        return reminderService.createReminder(request)
            .flatMapCompletable { fetchReminders() }
    }

    override fun deleteReminders(request: DeleteRemindersRequest): Completable {
        return reminderService.deleteReminders(request)
            .andThen(fetchReminders())
    }

    private fun fetchReminders(): Completable {
        return reminderService.getReminders()
            .flatMapCompletable {
                remindersSubject.onNext(it)
                return@flatMapCompletable Completable.complete()
            }
    }
}