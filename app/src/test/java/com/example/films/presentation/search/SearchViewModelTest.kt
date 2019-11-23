package com.example.films.presentation.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.films.data.enums.LoadState
import com.example.films.data.models.Movie
import com.example.films.data.sources.MovieDataSource
import com.example.films.domain.SearchMoviesUseCase
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var movieDataSource: MovieDataSource
    @Mock
    private lateinit var moviesObserver: Observer<LoadState<List<Movie>>>
    private lateinit var viewModel : SearchViewModel
    private lateinit var debounceScheduler : TestScheduler

    @Before
    fun setUp() {
        debounceScheduler = TestScheduler()
        val scheduler = Schedulers.trampoline()
        val searchMoviesUseCase = SearchMoviesUseCase(movieDataSource, scheduler, scheduler, debounceScheduler)
        viewModel = SearchViewModel(searchMoviesUseCase)
        viewModel.movies.observeForever(moviesObserver)
    }

    @Test
    fun queryChanged_emptyQuery() {
        viewModel.queryChanged("")
        debounceScheduler.advanceTimeBy(SearchMoviesUseCase.SEARCH_DEBOUNCE_TIME, SearchMoviesUseCase.SEARCH_DEBOUNCE_TIME_UNIT)
        assert(viewModel.movies.value == LoadState.Data(emptyList<Movie>()))
    }

    @Test
    fun queryChanged_success() {
        val movie = Movie(1, "", "", Date(), 0.0,"", "")
        `when`(movieDataSource.search("E")).thenReturn(Flowable.just(listOf(movie)))
        viewModel.queryChanged("E")
        debounceScheduler.advanceTimeBy(SearchMoviesUseCase.SEARCH_DEBOUNCE_TIME, SearchMoviesUseCase.SEARCH_DEBOUNCE_TIME_UNIT)
        verify(moviesObserver).onChanged(LoadState.Loading)
        verify(moviesObserver).onChanged(LoadState.Data(listOf(movie)))
    }

    @Test
    fun queryChanged_error() {
        val connectionError = IOException("Connection error")
        `when`(movieDataSource.search("E")).thenReturn(Flowable.error(connectionError))
        viewModel.queryChanged("E")
        debounceScheduler.advanceTimeBy(SearchMoviesUseCase.SEARCH_DEBOUNCE_TIME, SearchMoviesUseCase.SEARCH_DEBOUNCE_TIME_UNIT)
        verify(moviesObserver).onChanged(LoadState.Loading)
        verify(moviesObserver).onChanged(LoadState.Error(connectionError))
    }
}