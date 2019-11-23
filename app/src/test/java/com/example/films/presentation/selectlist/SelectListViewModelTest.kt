package com.example.films.presentation.selectlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.films.data.enums.LoadState
import com.example.films.data.models.MovieList
import com.example.films.data.requests.AddMovieToListRequest
import com.example.films.data.sources.MovieListDataSource
import com.example.films.domain.AddMovieToListUseCase
import com.example.films.domain.GetMovieListsUseCase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class SelectListViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieListDataSource: MovieListDataSource
    @Mock
    private lateinit var addMovieObserver : Observer<LoadState<Unit>>
    @Mock
    private lateinit var movieListsObserver : Observer<LoadState<List<MovieList>>>

    private lateinit var viewModel : SelectListViewModel

    @Before
    fun setUp() {
        val scheduler = Schedulers.trampoline()
        val getMovieListUseCase = GetMovieListsUseCase(movieListDataSource, scheduler, scheduler)
        val addMovieToListUseCase = AddMovieToListUseCase(movieListDataSource, scheduler, scheduler)
        viewModel = SelectListViewModel(getMovieListUseCase, addMovieToListUseCase)
        viewModel.movieListsState.observeForever(movieListsObserver)
        viewModel.addMovieToListState.observeForever(addMovieObserver)
    }

    @Test
    fun loadLists_success() {
        val movieList = MovieList(1, "", Date(), mutableListOf(), "")
        `when`(movieListDataSource.getMovieLists()).thenReturn(Flowable.just(listOf(movieList)))
        viewModel.loadLists()
        verify(movieListsObserver).onChanged(LoadState.Loading)
        verify(movieListsObserver).onChanged(LoadState.Data(listOf(movieList)))
    }

    @Test
    fun loadLists_error() {
        val connectionError = IOException("Connection error")
        `when`(movieListDataSource.getMovieLists()).thenReturn(Flowable.error(connectionError))
        viewModel.loadLists()
        verify(movieListsObserver).onChanged(LoadState.Loading)
        verify(movieListsObserver).onChanged(LoadState.Error(connectionError))
    }

    @Test
    fun addMovieToList_success() {
        val request = AddMovieToListRequest(1, 1)
        `when`(movieListDataSource.addMovieToList(request)).thenReturn(Completable.complete())
        viewModel.addMovieToList(1, 1)
        verify(addMovieObserver).onChanged(LoadState.Loading)
        verify(addMovieObserver).onChanged(LoadState.Data(Unit))
    }

    @Test
    fun addMovieToList_error() {
        val connectionError = IOException("Connection error")
        val request = AddMovieToListRequest(1, 1)
        `when`(movieListDataSource.addMovieToList(request)).thenReturn(Completable.error(connectionError))
        viewModel.addMovieToList(1, 1)
        verify(addMovieObserver).onChanged(LoadState.Loading)
        verify(addMovieObserver).onChanged(LoadState.Error(connectionError))
    }
}