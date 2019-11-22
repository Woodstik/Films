package com.example.films.domain

import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.models.UsersMovieLists
import com.example.films.data.sources.MovieListDataSource
import com.example.films.data.sources.ReminderDataSource
import com.example.films.utils.Optional
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.functions.BiFunction

class GetUserListsUseCase(
    private val movieListDataSource: MovieListDataSource,
    private val reminderDataSource: ReminderDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<Unit, UsersMovieLists>(schedulerIO, schedulerMainThread) {

    override fun onCreate(parameter: Unit?): Flowable<UsersMovieLists> {
        return Flowable.combineLatest(
            reminderDataSource.getNextReminder(),
            movieListDataSource.getMovieLists(),
            BiFunction { optional: Optional<MovieReminder>, movieLists: List<MovieList> ->
                UsersMovieLists(
                    optional.value,
                    movieLists
                )
            }
        )
    }
}
