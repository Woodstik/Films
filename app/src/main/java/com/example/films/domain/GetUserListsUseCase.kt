package com.example.films.domain

import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.models.UsersMovieLists
import com.example.films.data.sources.MovieListDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.functions.BiFunction

class GetUserListsUseCase(
    private val movieListDataSource: MovieListDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<Void, UsersMovieLists>(schedulerIO, schedulerMainThread) {

    override fun onCreate(parameter: Void?): Flowable<UsersMovieLists> {
        return Flowable.zip(
            movieListDataSource.getNextReminder(),
            movieListDataSource.getMovieLists(),
            BiFunction { nextReminder: MovieReminder, movieLists: List<MovieList> ->
                UsersMovieLists(
                    nextReminder,
                    movieLists
                )
            }
        )
    }
}
