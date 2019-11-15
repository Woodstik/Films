package com.example.films.data.sources.repositories

import com.example.films.data.jobs.JobManager
import com.example.films.data.models.Movie
import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.AddMovieToListRequest
import com.example.films.data.requests.CreateMovieListRequest
import com.example.films.data.requests.CreateReminderRequest
import com.example.films.data.sources.remote.MovieListsService
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import java.util.*

class MovieListRepositoryTest {

    @Test
    fun nextReminderNotFound() {
        val movieListDataSource = MovieListRepository(object : MovieListsService {
            override fun createMovieList(request: CreateMovieListRequest): Single<Long> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun addMovieToList(request: AddMovieToListRequest): Completable {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun createReminder(request: CreateReminderRequest): Completable {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getReminders(): Single<List<MovieReminder>> = Single.just(emptyList())
            override fun getMovieLists(): Single<List<MovieList>> =
                Single.error(UnsupportedOperationException("Method not implemented"))
        }, object : JobManager{
            override fun scheduleReminder(movie: Movie, remindDate: Date): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        val testSubscriber = movieListDataSource.getNextReminder().test()
        testSubscriber.assertValueCount(1)
        testSubscriber.assertComplete()
    }

    @Test
    fun emptyMovieLists() {
        val movieListDataSource = MovieListRepository(object : MovieListsService {
            override fun addMovieToList(request: AddMovieToListRequest): Completable {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun createReminder(request: CreateReminderRequest): Completable {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun createMovieList(request: CreateMovieListRequest): Single<Long> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getReminders(): Single<List<MovieReminder>> =
                Single.error(UnsupportedOperationException("Method not implemented"))

            override fun getMovieLists(): Single<List<MovieList>> = Single.just(emptyList())
        }, object : JobManager{
            override fun scheduleReminder(movie: Movie, remindDate: Date): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        val testSubscriber = movieListDataSource.getMovieLists().test()
        testSubscriber.assertValueCount(1)
        testSubscriber.assertComplete()
    }
}
