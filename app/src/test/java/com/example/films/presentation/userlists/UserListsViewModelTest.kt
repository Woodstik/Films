package com.example.films.presentation.userlists

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.films.data.enums.LoadState
import com.example.films.data.models.Movie
import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.models.UsersMovieLists
import com.example.films.data.sources.MovieListDataSource
import com.example.films.data.sources.ReminderDataSource
import com.example.films.domain.DeleteMovieListUseCase
import com.example.films.domain.GetUserListsUseCase
import com.example.films.utils.Optional
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
class UserListsViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var reminderDataSource: ReminderDataSource
    @Mock
    private lateinit var movieListDataSource: MovieListDataSource
    @Mock
    private lateinit var userListsObserver: Observer<LoadState<UsersMovieLists>>
    private lateinit var viewModel : UserListsViewModel

    @Before
    fun setUp() {
        val scheduler = Schedulers.trampoline()
        val getUsersListsUseCase = GetUserListsUseCase(movieListDataSource, reminderDataSource, scheduler, scheduler)
        val deleteMovieListUseCase = DeleteMovieListUseCase(movieListDataSource, scheduler, scheduler)
        viewModel = UserListsViewModel(getUsersListsUseCase, deleteMovieListUseCase)
        viewModel.usersMovieLists.observeForever(userListsObserver)
    }

    @Test
    fun loadLists_success() {
        val movieList = MovieList(1, "", Date(), mutableListOf(), "")
        `when`(movieListDataSource.getMovieLists()).thenReturn(Flowable.just(listOf(movieList)))
        val reminder = MovieReminder(1, Movie(1, "", "", Date(), 0.0, "", "",0), Date())
        `when`(reminderDataSource.getNextReminder()).thenReturn(Flowable.just(Optional(reminder)))
        viewModel.loadLists()
        verify(userListsObserver).onChanged(LoadState.Loading)
        verify(userListsObserver).onChanged(LoadState.Data(UsersMovieLists(reminder, listOf(movieList))))
    }

    @Test
    fun loadLists_NoReminderSuccess() {
        val movieList = MovieList(1, "", Date(), mutableListOf(), "")
        `when`(movieListDataSource.getMovieLists()).thenReturn(Flowable.just(listOf(movieList)))
        `when`(reminderDataSource.getNextReminder()).thenReturn(Flowable.just(Optional<MovieReminder>(null)))
        viewModel.loadLists()
        verify(userListsObserver).onChanged(LoadState.Loading)
        verify(userListsObserver).onChanged(LoadState.Data(UsersMovieLists(null, listOf(movieList))))
    }

    @Test
    fun loadLists_never() {
        `when`(movieListDataSource.getMovieLists()).thenReturn(Flowable.never())
        `when`(reminderDataSource.getNextReminder()).thenReturn(Flowable.just(Optional<MovieReminder>(null)))
        viewModel.loadLists()
        verify(userListsObserver).onChanged(LoadState.Loading)
        assert(viewModel.usersMovieLists.value == LoadState.Loading)
    }

    @Test
    fun loadLists_movieListError() {
        val connectionError = IOException("Connection error")
        `when`(movieListDataSource.getMovieLists()).thenReturn(Flowable.error(connectionError))
        `when`(reminderDataSource.getNextReminder()).thenReturn(Flowable.just(Optional<MovieReminder>(null)))
        viewModel.loadLists()
        verify(userListsObserver).onChanged(LoadState.Loading)
        verify(userListsObserver).onChanged(LoadState.Error(connectionError))
    }

    @Test
    fun loadLists_movieReminderError() {
        val connectionError = IOException("Connection error")
        `when`(movieListDataSource.getMovieLists()).thenReturn(Flowable.just(emptyList()))
        `when`(reminderDataSource.getNextReminder()).thenReturn(Flowable.error(connectionError))
        viewModel.loadLists()
        verify(userListsObserver).onChanged(LoadState.Loading)
        verify(userListsObserver).onChanged(LoadState.Error(connectionError))
    }
}