package com.example.films.data.sources.repositories

import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.sources.MovieListDataSource
import com.example.films.data.sources.remote.MovieListsService
import io.reactivex.Flowable

class MovieListRepository(private val movieListsService: MovieListsService) : MovieListDataSource {
    override fun getMovieLists(): Flowable<List<MovieList>> {
        return movieListsService.getMovieLists()
            .toFlowable()
    }

    override fun getNextReminder(): Flowable<MovieReminder> {
        return movieListsService.getReminders()
            .toFlowable()
            .flatMapIterable { r -> r }
            .take(1)
    }
}
