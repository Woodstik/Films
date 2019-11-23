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
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class GetMovieListUseCaseTest {

    @Mock
    private lateinit var movieListDataSource: MovieListDataSource
    private val scheduler = Schedulers.trampoline()

    @Before
    fun setUp() {
    }

    @Test
    fun getMovieLists_success(){
        val movieList = MovieList(1, "", Date(), mutableListOf(), "")
        Mockito.`when`(movieListDataSource.getMovieList(1)).thenReturn(Flowable.just(movieList))
        val testSubscriber = GetMovieListUseCase(movieListDataSource, scheduler, scheduler)
            .execute(1)
            .test()
        testSubscriber.assertValue { it == movieList }
        testSubscriber.assertComplete()
    }

    @Test
    fun getMovieList_error(){
        Mockito.`when`(movieListDataSource.getMovieList(1))
            .thenReturn(Flowable.error(IOException("Connection error")))
        val testSubscriber = GetMovieListUseCase(movieListDataSource, scheduler, scheduler)
            .execute(1)
            .test()
        testSubscriber.assertError { it is IOException }
    }

    @Test(expected = KotlinNullPointerException::class)
    fun getMovieList_noRequest() {
        GetMovieListUseCase(movieListDataSource, scheduler, scheduler)
            .execute()
            .test()
    }
}