package com.example.films.data.sources.repositories

import com.example.films.data.models.MovieList
import com.example.films.data.requests.AddMovieToListRequest
import com.example.films.data.requests.CreateMovieListRequest
import com.example.films.data.requests.DeleteMovieFromListRequest
import com.example.films.data.requests.EditListRequest
import com.example.films.data.sources.MovieListDataSource
import com.example.films.data.sources.remote.MovieListsService
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

class MovieListRepository(private val movieListsService: MovieListsService) : MovieListDataSource {

    private val movieListSubject = BehaviorSubject.create<List<MovieList>>()

    override fun getMovieLists(forceRefresh: Boolean): Flowable<List<MovieList>> {
        var fetchCompletable = Completable.complete()
        if(forceRefresh){
            fetchCompletable = fetchMovieLists()
        }
        return fetchCompletable
            .andThen(movieListSubject.toFlowable(BackpressureStrategy.DROP))
    }

    override fun getMovieList(listId: Long): Flowable<MovieList> {
        return movieListsService.getMovieList(listId)
            .toFlowable()
    }

    override fun deleteMovieFromList(request: DeleteMovieFromListRequest): Completable {
        return movieListsService.deleteMovieFromList(request)
            .andThen(fetchMovieLists())
    }

    override fun deleteMovieList(listId: Long): Completable {
        return movieListsService.deleteMovieList(listId)
            .andThen(fetchMovieLists())
    }

    override fun editMovieList(request: EditListRequest): Completable {
        return movieListsService.editMovieList(request)
            .andThen(fetchMovieLists())
    }

    override fun addMovieToList(request: AddMovieToListRequest): Completable {
        return movieListsService.addMovieToList(request)
            .andThen(fetchMovieLists())
    }

    override fun createList(request: CreateMovieListRequest): Flowable<Long> {
        return movieListsService.createMovieList(request)
            .toFlowable()
            .flatMap {
                return@flatMap fetchMovieLists()
                    .andThen(Flowable.just(it))
            }
    }

    private fun fetchMovieLists(): Completable {
        return movieListsService.getMovieLists()
            .flatMapCompletable {
                movieListSubject.onNext(it)
                return@flatMapCompletable Completable.complete()
            }
    }
}
