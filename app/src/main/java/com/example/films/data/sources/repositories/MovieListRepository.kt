package com.example.films.data.sources.repositories

import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.AddMovieToListRequest
import com.example.films.data.requests.CreateMovieListRequest
import com.example.films.data.requests.CreateReminderRequest
import com.example.films.data.requests.DeleteRemindersRequest
import com.example.films.data.sources.MovieListDataSource
import com.example.films.data.sources.remote.MovieListsService
import com.example.films.utils.Optional
import io.reactivex.Completable
import io.reactivex.Flowable

class MovieListRepository(private val movieListsService: MovieListsService) : MovieListDataSource {

    override fun addMovieToList(request: AddMovieToListRequest): Completable {
        return movieListsService.addMovieToList(request)
    }

    override fun createList(request: CreateMovieListRequest): Flowable<Long> {
        return movieListsService.createMovieList(request)
            .toFlowable()
    }

    override fun getMovieLists(): Flowable<List<MovieList>> {
        return movieListsService.getMovieLists()
            .toFlowable()
    }

    override fun getNextReminder(): Flowable<Optional<MovieReminder>> {
        return movieListsService.getReminders()
            .toFlowable()
            .flatMapIterable { r -> r }
            .take(1)
            .map { reminder -> Optional(reminder) }
            .defaultIfEmpty(Optional(null))
    }

    override fun getReminders(): Flowable<List<MovieReminder>> {
        return movieListsService.getReminders()
            .toFlowable()
    }

    override fun getTodayReminders(): Flowable<List<MovieReminder>> {
        return movieListsService.getTodayReminders()
            .toFlowable()    }

    override fun createReminder(request: CreateReminderRequest): Completable {
        return movieListsService.createReminder(request)
            .flatMapCompletable { Completable.complete() }
    }

    override fun deleteReminders(request: DeleteRemindersRequest): Completable {
        return movieListsService.deleteReminders(request)
    }
}
