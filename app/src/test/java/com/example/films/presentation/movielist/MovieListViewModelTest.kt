package com.example.films.presentation.movielist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.films.data.enums.LoadState
import com.example.films.data.models.MovieList
import com.example.films.data.sources.MovieListDataSource
import com.example.films.domain.DeleteMovieFromListUseCase
import com.example.films.domain.DeleteMovieListUseCase
import com.example.films.domain.GetMovieListUseCase
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MovieListViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var movieListDataSource: MovieListDataSource
    @Mock
    private lateinit var movieListObserver: Observer<LoadState<MovieList>>
    private lateinit var viewModel : MovieListViewModel

    @Before
    fun setUp() {
        val scheduler = Schedulers.trampoline()
        val getMovieListUseCase = GetMovieListUseCase(movieListDataSource, scheduler, scheduler)
        val deleteMovieListUseCase = DeleteMovieListUseCase(movieListDataSource, scheduler, scheduler)
        val deleteMovieFromListUseCase = DeleteMovieFromListUseCase(movieListDataSource, scheduler, scheduler)
        viewModel = MovieListViewModel(getMovieListUseCase, deleteMovieFromListUseCase, deleteMovieListUseCase)
        viewModel.movieListState.observeForever(movieListObserver)
    }

    @Test
    fun loadMovieList_success() {
        val movieList = MovieList(1, "", Date(), mutableListOf(), "")
        `when`(movieListDataSource.getMovieList(1)).thenReturn(Flowable.just(movieList))
        viewModel.loadMovieList(1)
        verify(movieListObserver).onChanged(LoadState.Loading)
        verify(movieListObserver).onChanged(LoadState.Data(movieList))
    }

    @Test
    fun loadMovieList_error() {
        val connectionError = IOException("Connection error")
        `when`(movieListDataSource.getMovieList(1)).thenReturn(Flowable.error(connectionError))
        viewModel.loadMovieList(1)
        verify(movieListObserver).onChanged(LoadState.Loading)
        verify(movieListObserver).onChanged(LoadState.Error(connectionError))
    }
}