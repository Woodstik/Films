package com.example.films.data.sources.repositories

import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.sources.remote.MovieListsService
import io.reactivex.Single
import org.junit.Test

class MovieListRepositoryTest {

    @Test
    fun nextReminderNotFound() {
        val movieListDataSource = MovieListRepository(object : MovieListsService {
            override fun getReminders(): Single<List<MovieReminder>> = Single.just(emptyList())
            override fun getMovieLists(): Single<List<MovieList>> =
                Single.error(UnsupportedOperationException("Method not implemented"))
        })
        val testSubscriber = movieListDataSource.getNextReminder().test()
        testSubscriber.assertValueCount(1)
        testSubscriber.assertComplete()
    }

    @Test
    fun emptyMovieLists() {
        val movieListDataSource = MovieListRepository(object : MovieListsService {
            override fun getReminders(): Single<List<MovieReminder>> =
                Single.error(UnsupportedOperationException("Method not implemented"))

            override fun getMovieLists(): Single<List<MovieList>> = Single.just(emptyList())
        })
        val testSubscriber = movieListDataSource.getMovieLists().test()
        testSubscriber.assertValueCount(1)
        testSubscriber.assertComplete()
    }
}
