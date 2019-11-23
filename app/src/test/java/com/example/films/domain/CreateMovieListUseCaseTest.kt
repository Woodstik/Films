package com.example.films.domain

import com.example.films.data.requests.CreateMovieListRequest
import com.example.films.data.sources.MovieListDataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class CreateMovieListUseCaseTest {

    @Mock
    private lateinit var movieListDataSource: MovieListDataSource
    private val scheduler = Schedulers.trampoline()

    @Before
    fun setUp() {
    }

    @Test
    fun createMovieList_success() {
        val request = CreateMovieListRequest("List", "")
        `when`(movieListDataSource.createList(request)).thenReturn(Flowable.just(1L))
        val testSubscriber = CreateMovieListUseCase(movieListDataSource, scheduler, scheduler)
            .execute(request)
            .test()
        testSubscriber.assertValueCount(1)
        testSubscriber.assertComplete()
    }

    @Test
    fun createMovieList_error() {
        val request = CreateMovieListRequest("List", "")
        `when`(movieListDataSource.createList(request))
            .thenReturn(Flowable.error(IOException("Connection error")))
        val testSubscriber = CreateMovieListUseCase(movieListDataSource, scheduler, scheduler)
            .execute(request)
            .test()
        testSubscriber.assertError { it is IOException }
    }

    @Test(expected = KotlinNullPointerException::class)
    fun createMovieList_noRequest() {
        CreateMovieListUseCase(movieListDataSource, scheduler, scheduler)
            .execute()
            .test()
    }
}