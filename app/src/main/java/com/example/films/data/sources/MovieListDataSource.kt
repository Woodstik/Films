package com.example.films.data.sources

import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.AddMovieToListRequest
import com.example.films.data.requests.CreateMovieListRequest
import com.example.films.data.requests.CreateReminderRequest
import com.example.films.data.requests.DeleteRemindersRequest
import com.example.films.utils.Optional
import io.reactivex.Completable
import io.reactivex.Flowable

interface MovieListDataSource {
    fun getMovieLists(forceRefresh: Boolean = true): Flowable<List<MovieList>>
    fun createList(request: CreateMovieListRequest): Flowable<Long>
    fun addMovieToList(request: AddMovieToListRequest): Completable
    fun getMovieList(listId: Long): Flowable<MovieList>
}
