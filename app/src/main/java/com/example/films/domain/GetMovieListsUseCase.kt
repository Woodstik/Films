package com.example.films.domain

import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.models.UsersMovieLists
import com.example.films.data.sources.MovieListDataSource
import com.example.films.utils.Optional
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.functions.BiFunction

class GetMovieListsUseCase(
    private val movieListDataSource: MovieListDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<Void, List<MovieList>>(schedulerIO, schedulerMainThread) {

    override fun onCreate(parameter: Void?): Flowable<List<MovieList>> {
        return movieListDataSource.getMovieLists()
    }
}
