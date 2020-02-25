package com.example.films.domain

import com.example.films.data.models.Movie
import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.sources.MovieListDataSource
import com.example.films.data.sources.ReminderDataSource
import com.example.films.utils.Optional
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class GetUserListsUseCaseTest {

    @Mock
    private lateinit var reminderDataSource: ReminderDataSource
    @Mock
    private lateinit var movieListDataSource: MovieListDataSource
    private val scheduler = Schedulers.trampoline()

    @Before
    fun setUp() {
    }

    @Test
    fun getUserLists_success() {
        val movieList = MovieList(1L, "", Date(), mutableListOf(), "")
        `when`(movieListDataSource.getMovieLists()).thenReturn(Flowable.just(listOf(movieList)))
        val reminder = MovieReminder(1L, Movie(1, "", "", Date(), 0.0, "", "",0), Date())
        `when`(reminderDataSource.getNextReminder()).thenReturn(Flowable.just(Optional(reminder)))
        val testSubscriber =
            GetUserListsUseCase(movieListDataSource, reminderDataSource, scheduler, scheduler)
                .execute()
                .test()
        testSubscriber.assertValue { it.movieLists == listOf(movieList) && it.nextReminder == reminder }
        testSubscriber.assertComplete()
    }

    @Test
    fun getUserLists_multipleValues() {
        val movieListSubject = BehaviorSubject.create<List<MovieList>>()
        val reminderSubject = BehaviorSubject.create<Optional<MovieReminder>>()
        `when`(movieListDataSource.getMovieLists()).thenReturn(
            movieListSubject.toFlowable(
                BackpressureStrategy.DROP
            )
        )
        `when`(reminderDataSource.getNextReminder()).thenReturn(
            reminderSubject.toFlowable(
                BackpressureStrategy.DROP
            )
        )
        val testSubscriber =
            GetUserListsUseCase(movieListDataSource, reminderDataSource, scheduler, scheduler)
                .execute()
                .test()
        testSubscriber.assertEmpty()
        testSubscriber.assertNotComplete()
        movieListSubject.onNext(listOf())
        reminderSubject.onNext(Optional(null))
        testSubscriber.assertValueCount(1)
        testSubscriber.assertNotComplete()
        reminderSubject.onNext(Optional(null))
        testSubscriber.assertValueCount(2)
        testSubscriber.assertNotComplete()
    }

    @Test
    fun getUserLists_emptyReminder() {
        val movieList = MovieList(1L, "", Date(), mutableListOf(), "")
        `when`(movieListDataSource.getMovieLists()).thenReturn(Flowable.just(listOf(movieList)))
        `when`(reminderDataSource.getNextReminder())
            .thenReturn(Flowable.just(Optional<MovieReminder>(null)))
        val testSubscriber =
            GetUserListsUseCase(movieListDataSource, reminderDataSource, scheduler, scheduler)
                .execute()
                .test()
        testSubscriber.assertValue { it.movieLists == listOf(movieList) && it.nextReminder == null }
        testSubscriber.assertComplete()
    }

    @Test
    fun getUserLists_emptyMovieList() {
        `when`(movieListDataSource.getMovieLists()).thenReturn(Flowable.just(emptyList()))
        val reminder = MovieReminder(1L, Movie(1, "", "", Date(), 0.0, "", "",0), Date())
        `when`(reminderDataSource.getNextReminder()).thenReturn(Flowable.just(Optional(reminder)))
        val testSubscriber =
            GetUserListsUseCase(movieListDataSource, reminderDataSource, scheduler, scheduler)
                .execute()
                .test()
        testSubscriber.assertValue { it.movieLists.isEmpty() && it.nextReminder == reminder }
        testSubscriber.assertComplete()
    }

    @Test
    fun getUserLists_errorReminder() {
        `when`(movieListDataSource.getMovieLists()).thenReturn(Flowable.just(emptyList()))
        `when`(reminderDataSource.getNextReminder()).thenReturn(Flowable.error(IOException("Connection error")))
        val testSubscriber =
            GetUserListsUseCase(movieListDataSource, reminderDataSource, scheduler, scheduler)
                .execute()
                .test()
        testSubscriber.assertError { it is IOException }
    }

    @Test
    fun getUserLists_errorMovieList() {
        `when`(movieListDataSource.getMovieLists()).thenReturn(Flowable.error(IOException("Connection error")))
        `when`(reminderDataSource.getNextReminder())
            .thenReturn(Flowable.just(Optional<MovieReminder>(null)))
        val testSubscriber =
            GetUserListsUseCase(movieListDataSource, reminderDataSource, scheduler, scheduler)
                .execute()
                .test()
        testSubscriber.assertError { it is IOException }
    }
}
