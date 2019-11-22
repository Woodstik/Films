package com.example.films.domain

import com.example.films.data.models.Movie
import com.example.films.data.sources.MovieDataSource
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
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
class SearchMoviesUseCaseTest {

    @Mock
    private lateinit var movieDataSource: MovieDataSource
    private val scheduler = Schedulers.trampoline()

    @Before
    fun setUp() {
    }

    @Test
    fun searchMovies_success(){
        val movie = Movie(1, "", "", Date(), 0.0, "", "")
        `when`(movieDataSource.search("")).thenReturn(Flowable.just(listOf(movie)))
        val testSubscriber = SearchMoviesUseCase(movieDataSource,scheduler,scheduler)
            .execute("")
            .test()
        testSubscriber.assertValue { it == listOf(movie) }
        testSubscriber.assertComplete()
    }

    @Test
    fun searchMovies_emptyList(){
        `when`(movieDataSource.search("")).thenReturn(Flowable.just(emptyList()))
        val testSubscriber = SearchMoviesUseCase(movieDataSource,scheduler,scheduler)
            .execute("")
            .test()
        testSubscriber.assertValue { it.isEmpty() }
        testSubscriber.assertComplete()
    }

    @Test
    fun searchMovies_error(){
        `when`(movieDataSource.search("")).thenReturn(Flowable.error(IOException("Connection Error")))
        val testSubscriber = SearchMoviesUseCase(movieDataSource,scheduler,scheduler)
            .execute("")
            .test()
        testSubscriber.assertError { it is IOException }
    }

    @Test(expected = KotlinNullPointerException::class)
    fun search_noRequest() {
        SearchMoviesUseCase(movieDataSource, scheduler, scheduler)
            .execute()
            .test()
    }
}