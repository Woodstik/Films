package com.example.films.domain

import com.example.films.data.requests.AddMovieToListRequest
import com.example.films.data.sources.MovieListDataSource
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class AddMovieToListUseCaseTest {

    @Mock
    private lateinit var movieListDataSource: MovieListDataSource
    private val scheduler = Schedulers.trampoline()

    @Before
    fun setUp() {
    }

    @Test
    fun addMovieToList_success() {
        val request = AddMovieToListRequest(1, 1)
        `when`(movieListDataSource.addMovieToList(request)).thenReturn(Completable.complete())
        val testSubscriber = AddMovieToListUseCase(movieListDataSource, scheduler, scheduler)
            .execute(request)
            .test()
        testSubscriber.assertValueCount(1)
        testSubscriber.assertComplete()
    }

    @Test
    fun addMovieToList_error() {
        val request = AddMovieToListRequest(1, 1)
        `when`(movieListDataSource.addMovieToList(request)).thenReturn(Completable.error(IOException("Connection error")))
        val testSubscriber = AddMovieToListUseCase(movieListDataSource, scheduler, scheduler)
            .execute(request)
            .test()
        testSubscriber.assertError { it is IOException }
    }

    @Test(expected = KotlinNullPointerException::class)
    fun addMovieToList_noRequest() {
        AddMovieToListUseCase(movieListDataSource, scheduler, scheduler)
            .execute()
            .test()
    }
}