package com.example.films.presentation.editlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.films.data.enums.LoadState
import com.example.films.data.requests.AddMovieToListRequest
import com.example.films.data.requests.CreateMovieListRequest
import com.example.films.data.sources.MovieListDataSource
import com.example.films.domain.AddMovieToListUseCase
import com.example.films.domain.CreateMovieListUseCase
import com.example.films.domain.EditListUseCase
import com.example.films.domain.GetMovieListUseCase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class EditListViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieListDataSource: MovieListDataSource
    @Mock
    private lateinit var createListObserver: Observer<LoadState<Unit>>
    private lateinit var viewModel: EditListViewModel

    @Before
    fun setUp() {
        val scheduler = Schedulers.trampoline()
        val createMovieListUseCase = CreateMovieListUseCase(movieListDataSource, scheduler, scheduler)
        val addMovieToListUseCase = AddMovieToListUseCase(movieListDataSource, scheduler, scheduler)
        val getMovieListUseCase = GetMovieListUseCase(movieListDataSource, scheduler, scheduler)
        val editListUseCase = EditListUseCase(movieListDataSource, scheduler, scheduler)

        viewModel = EditListViewModel(createMovieListUseCase, addMovieToListUseCase, getMovieListUseCase, editListUseCase)
        viewModel.submitListState.observeForever(createListObserver)
    }

    @Test
    fun createMovieList_noMovieSuccess() {
        val request = CreateMovieListRequest("Title", "")
        `when`(movieListDataSource.createList(request))
            .thenReturn(Flowable.just(1))
        viewModel.submit("Title")
        verify(createListObserver).onChanged(LoadState.Loading)
        verify(createListObserver).onChanged(LoadState.Data(Unit))
    }

    @Test
    fun createMovieList_noMovieError() {
        val connectionError = IOException("Connection error")
        val request = CreateMovieListRequest("Title", "")
        `when`(movieListDataSource.createList(request))
            .thenReturn(Flowable.error(connectionError))
        viewModel.submit(request.title)
        verify(createListObserver).onChanged(LoadState.Loading)
        verify(createListObserver).onChanged(LoadState.Error(connectionError))
    }

    @Test
    fun createMovieList_withMovieSuccess() {
        val createRequest = CreateMovieListRequest("Title", "")
        `when`(movieListDataSource.createList(createRequest))
            .thenReturn(Flowable.just(1))
        val addMovieRequest = AddMovieToListRequest(1, 1)
        `when`(movieListDataSource.addMovieToList(addMovieRequest))
            .thenReturn(Completable.complete())
        viewModel.submit(createRequest.title)
        verify(createListObserver).onChanged(LoadState.Loading)
        verify(createListObserver).onChanged(LoadState.Data(Unit))
    }

    @Test
    fun createMovieList_withMovieError() {
        val connectionError = IOException("Connection error")
        val createRequest = CreateMovieListRequest("Title", "")
        `when`(movieListDataSource.createList(createRequest))
            .thenReturn(Flowable.error(connectionError))
        viewModel.submit(createRequest.title)
        verify(createListObserver).onChanged(LoadState.Loading)
        verify(createListObserver).onChanged(LoadState.Error(connectionError))
    }
}