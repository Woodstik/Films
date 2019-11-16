package com.example.films.data.sources.remote

import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.AddMovieToListRequest
import com.example.films.data.requests.CreateMovieListRequest
import com.example.films.data.requests.CreateReminderRequest
import com.example.films.data.requests.DeleteRemindersRequest
import io.reactivex.Completable
import io.reactivex.Single

interface MovieListsService {
    fun getReminders(): Single<List<MovieReminder>>
    fun getMovieLists(): Single<List<MovieList>>
    fun createMovieList(request: CreateMovieListRequest): Single<Long>
    fun addMovieToList(request: AddMovieToListRequest): Completable
    fun getReminder(id: Long) : Single<MovieReminder>
    fun createReminder(request: CreateReminderRequest): Single<Long>
    fun deleteReminders(request: DeleteRemindersRequest): Completable
    fun getTodayReminders(): Single<List<MovieReminder>>
}
