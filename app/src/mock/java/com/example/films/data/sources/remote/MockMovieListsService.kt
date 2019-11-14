package com.example.films.data.sources.remote

import com.example.films.TestData
import com.example.films.data.models.Movie
import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.AddMovieToListRequest
import com.example.films.data.requests.CreateMovieListRequest
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

class MockMovieListsService(private val movies: TestData.Movies) : MovieListsService {

    override fun addMovieToList(request: AddMovieToListRequest): Completable {
        val movieList = movieLists.find { it.id == request.listId }!!
        movieList.movies.add(movies.getById(request.movieId))
        return Completable.complete()
    }

    private val movieLists = mutableListOf<MovieList>()

    init {
        movieLists.add(MovieList(1, "Watchlist", Date(), mutableListOf(), "#F44336"))
        movieLists.add(MovieList(2, "Collection", Date(), mutableListOf(), "#E91E63"))
    }

    override fun createMovieList(request: CreateMovieListRequest): Single<Long> {
        movieLists.add(MovieList((movieLists.size + 1).toLong(), request.title, Date(), mutableListOf(), request.color))
        return Single.just(movieLists.size.toLong())
    }

    override fun getReminders(): Single<List<MovieReminder>> {
        return Single.just(
            listOf(
                MovieReminder(1,movies.getById(320288), Date())
            )
        )
    }

    override fun getMovieLists(): Single<List<MovieList>> {
        return Single.just(movieLists)
    }
}
