package com.example.films.data.sources.remote

import com.example.films.TestData
import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.AddMovieToListRequest
import com.example.films.data.requests.CreateMovieListRequest
import com.example.films.data.requests.CreateReminderRequest
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

class MockMovieListsService(private val movies: TestData.Movies) : MovieListsService {

    private val movieLists = mutableListOf<MovieList>()
    private val reminders = mutableListOf<MovieReminder>()

    init {
        movieLists.add(MovieList(1, "Watchlist", Date(), mutableListOf(), "#F44336"))
        movieLists.add(MovieList(2, "Collection", Date(), mutableListOf(), "#E91E63"))
    }

    override fun getMovieLists(): Single<List<MovieList>> {
        return Single.just(movieLists)
    }

    override fun addMovieToList(request: AddMovieToListRequest): Completable {
        val movieList = movieLists.find { it.id == request.listId }!!
        movieList.movies.add(movies.getById(request.movieId))
        return Completable.complete()
    }

    override fun createMovieList(request: CreateMovieListRequest): Single<Long> {
        movieLists.add(MovieList((movieLists.size + 1).toLong(), request.title, Date(), mutableListOf(), request.color))
        return Single.just(movieLists.size.toLong())
    }

    override fun getReminders(): Single<List<MovieReminder>> {
        return Single.just(reminders)
    }

    override fun createReminder(request: CreateReminderRequest): Completable {
        val movie = movies.getById(request.movieId)
        reminders.add(MovieReminder((movieLists.size + 1).toLong(), movie, request.remindDate ?: movie.releaseDate))
        return Completable.complete()
    }
}
