package com.example.films.data.sources.remote

import com.example.films.TestData
import com.example.films.data.models.MovieList
import com.example.films.data.requests.AddMovieToListRequest
import com.example.films.data.requests.CreateMovieListRequest
import com.example.films.data.requests.DeleteMovieFromListRequest
import com.example.films.data.requests.EditListRequest
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

class MockMovieListsService(private val testMovies: TestData.Movies) : MovieListsService {

    private val movieLists = mutableListOf<MovieList>()

    init {
        movieLists.add(MovieList(1, "Watchlist", Date(), mutableListOf(), "#F44336"))
        movieLists.add(MovieList(2, "Collection", Date(), mutableListOf(), "#E91E63"))
    }

    override fun getMovieLists(): Single<List<MovieList>> {
        return Single.just(movieLists)
    }

    override fun addMovieToList(request: AddMovieToListRequest): Completable {
        val movieList = movieLists.find { it.id == request.listId }!!
        val movies = movieList.movies.toMutableList()
        movies.add(testMovies.getById(request.movieId))
        movieLists[movieLists.indexOf(movieList)] = movieList.copy(movies = movies)
        return Completable.complete()
    }

    override fun deleteMovieFromList(request: DeleteMovieFromListRequest): Completable {
        val movieList = movieLists.find { it.id == request.listId }!!
        val movies = movieList.movies.toMutableList()
        movies.remove(movieList.movies.find { it.id == request.movieId })
        movieLists[movieLists.indexOf(movieList)] = movieList.copy(movies = movies)
        return Completable.complete()
    }

    override fun deleteMovieList(listId: Long): Completable {
        val movieList = movieLists.find { it.id == listId }!!
        movieLists.remove(movieList)
        return Completable.complete()
    }

    override fun editMovieList(request: EditListRequest): Completable {
        val movieList = movieLists.find { it.id == request.listId }!!
        if (request.title != null) {
            movieLists[movieLists.indexOf(movieList)] = movieList.copy(title = request.title)
        }
        return Completable.complete()
    }

    override fun createMovieList(request: CreateMovieListRequest): Single<Long> {
        movieLists.add(MovieList((movieLists.size + 1).toLong(), request.title, Date(), mutableListOf(), request.color))
        return Single.just(movieLists.size.toLong())
    }

    override fun getMovieList(listId: Long): Single<MovieList> {
        return Single.just(movieLists.find { it.id == listId })
    }
}
