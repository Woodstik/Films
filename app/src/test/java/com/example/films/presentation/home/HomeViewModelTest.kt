package com.example.films.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.films.data.enums.LoadState
import com.example.films.data.models.HomeMovies
import com.example.films.data.requests.CreateReminderRequest
import com.example.films.data.sources.MovieDataSource
import com.example.films.data.sources.PostDataSource
import com.example.films.data.sources.ReminderDataSource
import com.example.films.domain.CreateReminderUseCase
import com.example.films.domain.GetHomeUseCase
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
class HomeViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieDataSource: MovieDataSource
    @Mock
    private lateinit var postDataSource: PostDataSource
    @Mock
    private lateinit var reminderDataSource: ReminderDataSource
    @Mock
    private lateinit var moviesObserver: Observer<LoadState<HomeMovies>>
    @Mock
    private lateinit var createReminderObserver: Observer<LoadState<Unit>>

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        val scheduler = Schedulers.trampoline()
        val getHomeUseCase = GetHomeUseCase(movieDataSource, postDataSource, scheduler, scheduler)
        val createReminderUseCase = CreateReminderUseCase(reminderDataSource, scheduler, scheduler)
        viewModel = HomeViewModel(getHomeUseCase, createReminderUseCase)
        viewModel.moviesState.observeForever(moviesObserver)
        viewModel.createReminderState.observeForever(createReminderObserver)
    }

    @Test
    fun loadMovies_success() {
        `when`(movieDataSource.getUpcomingMovies()).thenReturn(Flowable.just(emptyList()))
        `when`(movieDataSource.getNewReleases()).thenReturn(Flowable.just(emptyList()))
        `when`(postDataSource.getPosts()).thenReturn(Flowable.just(emptyList()))
        viewModel.loadMovies()
        verify(moviesObserver).onChanged(LoadState.Loading)
        verify(moviesObserver).onChanged(
            LoadState.Data(
                HomeMovies(
                    emptyList(),
                    emptyList(),
                    emptyList()
                )
            )
        )
    }

    @Test
    fun loadMovies_error() {
        val connectionError = IOException("Connection error")
        `when`(movieDataSource.getUpcomingMovies()).thenReturn(Flowable.error(connectionError))
        `when`(movieDataSource.getNewReleases()).thenReturn(Flowable.just(emptyList()))
        `when`(postDataSource.getPosts()).thenReturn(Flowable.just(emptyList()))
        viewModel.loadMovies()
        verify(moviesObserver).onChanged(LoadState.Loading)
        verify(moviesObserver).onChanged(LoadState.Error(connectionError))
    }

    @Test
    fun createReminder_success() {
        val request = CreateReminderRequest(1)
        `when`(reminderDataSource.createReminder(request)).thenReturn(Completable.complete())
        viewModel.createReminder(1)
        verify(createReminderObserver).onChanged(LoadState.Loading)
        verify(createReminderObserver).onChanged(LoadState.Data(Unit))
    }

    @Test
    fun createReminder_error() {
        val connectionError = IOException("Connection error")
        val request = CreateReminderRequest(1)
        `when`(reminderDataSource.createReminder(request)).thenReturn(
            Completable.error(
                connectionError
            )
        )
        viewModel.createReminder(1)
        verify(createReminderObserver).onChanged(LoadState.Loading)
        verify(createReminderObserver).onChanged(LoadState.Error(connectionError))
    }
}
