package com.example.films.domain

import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.sources.remote.MovieListsService
import com.example.films.data.sources.repositories.MovieListRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class GetUserListsUseCaseTest {

    @Test
    fun emptyReminderAndMovieLists() {
        val movieListDataSource = MovieListRepository(object : MovieListsService {
            override fun getReminders(): Single<List<MovieReminder>> = Single.just(emptyList())
            override fun getMovieLists(): Single<List<MovieList>> = Single.just(emptyList())
        })
        val useCase = GetUserListsUseCase(
            movieListDataSource,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
        val testSubscriber = useCase.execute().test()
        testSubscriber.assertValueCount(1)
        testSubscriber.assertComplete()
    }
}
