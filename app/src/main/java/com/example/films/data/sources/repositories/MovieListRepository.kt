package com.example.films.data.sources.repositories

import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.CreateMovieListRequest
import com.example.films.data.sources.MovieListDataSource
import com.example.films.data.sources.remote.MovieListsService
import com.example.films.utils.Optional
import io.reactivex.Completable
import io.reactivex.Flowable

class MovieListRepository(private val movieListsService: MovieListsService) : MovieListDataSource {

    override fun createList(request: CreateMovieListRequest): Flowable<Int> {
        return movieListsService.createMovieList(request)
            .toFlowable()
    }

    override fun addMovieToList(movieId: Int, listId: Int): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
            .map {reminder -> Optional(reminder)}
            .defaultIfEmpty(Optional(null))
    }
}
