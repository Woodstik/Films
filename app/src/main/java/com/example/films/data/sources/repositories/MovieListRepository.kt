package com.example.films.data.sources.repositories

import com.example.films.TestData
import com.example.films.data.jobs.JobManager
import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.AddMovieToListRequest
import com.example.films.data.requests.CreateMovieListRequest
import com.example.films.data.requests.CreateReminderRequest
import com.example.films.data.sources.MovieListDataSource
import com.example.films.data.sources.remote.MovieListsService
import com.example.films.utils.Optional
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*

class MovieListRepository(
    private val movieListsService: MovieListsService,
    private val jobManager: JobManager
) : MovieListDataSource {

    override fun addMovieToList(request: AddMovieToListRequest): Completable {
        return movieListsService.addMovieToList(request)
    }

    override fun createList(request: CreateMovieListRequest): Flowable<Long> {
        return movieListsService.createMovieList(request)
            .toFlowable()
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
            .map { reminder -> Optional(reminder) }
            .defaultIfEmpty(Optional(null))
    }

    override fun createReminder(request: CreateReminderRequest): Completable {
        return movieListsService.createReminder(request)
            .andThen(scheduleReminder(request))
    }

    private fun scheduleReminder(request: CreateReminderRequest): Completable {
        //FIXME: This should not reference TestData.Movies
        val movie = TestData.Movies.getById(request.movieId)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, 60)
        jobManager.scheduleReminder(movie, request.remindDate ?: calendar.time)
        return Completable.complete()
    }
}
