package com.example.films.domain

import com.example.films.data.models.MovieList
import com.example.films.data.sources.MovieListDataSource
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class GetMovieListsUseCaseTest {

    @Mock
    private lateinit var movieListDataSource: MovieListDataSource
    private val scheduler = Schedulers.trampoline()

    @Before
    fun setUp() {
    }

    @Test
    fun getMovieLists_success(){
        val movieList = MovieList(1L, "", Date(), mutableListOf(), "")
        `when`(movieListDataSource.getMovieLists()).thenReturn(Flowable.just(listOf(movieList)))
        val testSubscriber = GetMovieListsUseCase(movieListDataSource, scheduler, scheduler)
            .execute()
            .test()
        testSubscriber.assertValue { it == listOf(movieList) }
        testSubscriber.assertComplete()
    }

    @Test
    fun getMovieLists_emptyList(){
        `when`(movieListDataSource.getMovieLists()).thenReturn(Flowable.just(emptyList()))
        val testSubscriber = GetMovieListsUseCase(movieListDataSource, scheduler, scheduler)
            .execute()
            .test()
        testSubscriber.assertValue { it.isEmpty() }
        testSubscriber.assertComplete()
    }

    @Test
    fun getMovieLists_multipleValues(){
        val subject = BehaviorSubject.create<List<MovieList>>()
        `when`(movieListDataSource.getMovieLists()).thenReturn(subject.toFlowable(BackpressureStrategy.DROP))
        val testSubscriber = GetMovieListsUseCase(movieListDataSource, scheduler, scheduler)
            .execute()
            .test()
        testSubscriber.assertEmpty()
        testSubscriber.assertNotComplete()
        subject.onNext(emptyList())
        testSubscriber.assertValueCount(1)
        testSubscriber.assertNotComplete()
        subject.onNext(emptyList())
        testSubscriber.assertValueCount(2)
        testSubscriber.assertNotComplete()
    }

    @Test
    fun getMovieLists_error(){
        `when`(movieListDataSource.getMovieLists()).thenReturn(Flowable.error(IOException("Connection error")))
        val testSubscriber = GetMovieListsUseCase(movieListDataSource, scheduler, scheduler)
            .execute()
            .test()
        testSubscriber.assertError { it is IOException }
    }
}