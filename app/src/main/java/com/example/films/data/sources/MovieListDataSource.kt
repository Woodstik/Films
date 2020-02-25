package com.example.films.data.sources

import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.*
import com.example.films.utils.Optional
import io.reactivex.Completable
import io.reactivex.Flowable

interface MovieListDataSource {
    fun getMovieLists(forceRefresh: Boolean = true): Flowable<List<MovieList>>
    fun createList(request: CreateMovieListRequest): Flowable<Long>
    fun addMovieToList(request: AddMovieToListRequest): Completable
    fun getMovieList(listId: Long): Flowable<MovieList>
    fun deleteMovieFromList(request: DeleteMovieFromListRequest) : Completable
    fun deleteMovieList(listId: Long): Completable
    fun editMovieList(request: EditListRequest): Completable
    fun toggleMovieWatched(movieId: Int): Completable
}
