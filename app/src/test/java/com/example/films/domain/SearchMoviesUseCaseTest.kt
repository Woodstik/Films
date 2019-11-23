package com.example.films.domain

import com.example.films.data.models.Movie
import com.example.films.data.sources.MovieDataSource
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before

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
    private val debounceScheduler = TestScheduler()

    private lateinit var useCase: SearchMoviesUseCase
    private lateinit var testSubscriber: TestSubscriber<List<Movie>>

    @Before
    fun setUp() {
        useCase = SearchMoviesUseCase(movieDataSource, scheduler, scheduler, debounceScheduler)
        useCase.setUp { query ->
            testSubscriber = useCase
                .execute(query)
                .test()
        }
    }

    @Test
    fun searchMovies_emptyQuery() {
        useCase.search("")
        debounceScheduler.advanceTimeBy(
            SearchMoviesUseCase.SEARCH_DEBOUNCE_TIME,
            SearchMoviesUseCase.SEARCH_DEBOUNCE_TIME_UNIT
        )
        testSubscriber.assertValue { it.isEmpty() }
        testSubscriber.assertComplete()
    }

    @Test
    fun searchMovies_success() {
        val movie = Movie(1, "", "", Date(), 0.0, "", "")
        `when`(movieDataSource.search("E")).thenReturn(Flowable.just(listOf(movie)))
        useCase.search("E")
        debounceScheduler.advanceTimeBy(
            SearchMoviesUseCase.SEARCH_DEBOUNCE_TIME,
            SearchMoviesUseCase.SEARCH_DEBOUNCE_TIME_UNIT
        )
        testSubscriber.assertValue { it == listOf(movie) }
        testSubscriber.assertComplete()
    }

    @Test
    fun searchMovies_error() {
        `when`(movieDataSource.search("E")).thenReturn(Flowable.error(IOException("Connection Error")))
        useCase.search("E")
        debounceScheduler.advanceTimeBy(
            SearchMoviesUseCase.SEARCH_DEBOUNCE_TIME,
            SearchMoviesUseCase.SEARCH_DEBOUNCE_TIME_UNIT
        )
        testSubscriber.assertError { it is IOException }
    }

    @Test(expected = KotlinNullPointerException::class)
    fun search_noRequest() {
        SearchMoviesUseCase(movieDataSource, scheduler, scheduler, debounceScheduler)
            .execute()
            .test()
    }
}