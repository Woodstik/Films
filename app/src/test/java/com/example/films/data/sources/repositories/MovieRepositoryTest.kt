package com.example.films.data.sources.repositories

import com.example.films.data.models.Movie
import com.example.films.data.sources.MovieDataSource
import com.example.films.data.sources.remote.MovieService
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {

    @Mock
    private lateinit var movieService: MovieService
    private lateinit var movieDataSource: MovieDataSource

    @Before
    fun setUp() {
        movieDataSource = MovieRepository(movieService)
    }

    @Test
    fun getNewReleases_emptyList() {
        `when`(movieService.newReleases()).thenReturn(Single.just(emptyList()))
        val testSubscriber = movieDataSource.getNewReleases().test()
        testSubscriber.assertValue { it.isEmpty() }
        testSubscriber.assertComplete()
    }

    @Test
    fun getNewReleases_successValue() {
        val movie = Movie(1, "", "", Date(), 0.0, "", "", 0)
        `when`(movieService.newReleases()).thenReturn(Single.just(listOf(movie)))
        val testSubscriber = movieDataSource.getNewReleases().test()
        testSubscriber.assertValue { it.contains(movie) }
        testSubscriber.assertComplete()
    }

    @Test
    fun getNewReleases_serviceError() {
        `when`(movieService.newReleases()).thenReturn(Single.error(IOException("Connection error")))
        val testSubscriber = movieDataSource.getNewReleases().test()
        testSubscriber.assertError { it is IOException }
    }

    @Test
    fun getUpcomingMovies_emptyList() {
        `when`(movieService.upcomingMovies()).thenReturn(Single.just(emptyList()))
        val testSubscriber = movieDataSource.getUpcomingMovies().test()
        testSubscriber.assertValue { it.isEmpty() }
        testSubscriber.assertComplete()
    }

    @Test
    fun getUpcomingMovies_successValue() {
        val movie = Movie(1, "", "", Date(), 0.0, "", "", 0)
        `when`(movieService.upcomingMovies()).thenReturn(Single.just(listOf(movie)))
        val testSubscriber = movieDataSource.getUpcomingMovies().test()
        testSubscriber.assertValue { it.contains(movie) }
        testSubscriber.assertComplete()
    }

    @Test
    fun getUpcomingMovies_serviceError() {
        `when`(movieService.upcomingMovies()).thenReturn(Single.error(IOException("Connection error")))
        val testSubscriber = movieDataSource.getUpcomingMovies().test()
        testSubscriber.assertError { it is IOException }
    }

    @Test
    fun search_emptyList() {
        `when`(movieService.search("")).thenReturn(Single.just(emptyList()))
        val testSubscriber = movieDataSource.search("").test()
        testSubscriber.assertValue { it.isEmpty() }
        testSubscriber.assertComplete()
    }

    @Test
    fun search_successValue() {
        val movie = Movie(1, "", "", Date(), 0.0, "", "", 0)
        `when`(movieService.search("")).thenReturn(Single.just(listOf(movie)))
        val testSubscriber = movieDataSource.search("").test()
        testSubscriber.assertValue { it.contains(movie) }
        testSubscriber.assertComplete()
    }

    @Test
    fun search_serviceError() {
        `when`(movieService.search("")).thenReturn(Single.error(IOException("Connection error")))
        val testSubscriber = movieDataSource.search("").test()
        testSubscriber.assertError { it is IOException }
    }
}
