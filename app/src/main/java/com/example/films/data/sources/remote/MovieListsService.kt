package com.example.films.data.sources.remote

import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.*
import io.reactivex.Completable
import io.reactivex.Single

interface MovieListsService {
    fun getMovieLists(): Single<List<MovieList>>
    fun getMovieList(listId: Long): Single<MovieList>
    fun createMovieList(request: CreateMovieListRequest): Single<Long>
    fun addMovieToList(request: AddMovieToListRequest): Completable
    fun deleteMovieFromList(request: DeleteMovieFromListRequest): Completable
    fun deleteMovieList(listId: Long): Completable
    fun editMovieList(request: EditListRequest): Completable
    fun toggleMovieWatched(movieId: Int): Completable
}
