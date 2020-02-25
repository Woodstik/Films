package com.example.films.presentation.reminders

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.films.data.enums.LoadState
import com.example.films.data.models.Movie
import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.DeleteRemindersRequest
import com.example.films.data.sources.ReminderDataSource
import com.example.films.domain.DeleteRemindersUseCase
import com.example.films.domain.GetRemindersUseCase
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
class RemindersViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var reminderDataSource: ReminderDataSource
    @Mock
    private lateinit var remindersObserver: Observer<LoadState<List<MovieReminder>>>
    @Mock
    private lateinit var deleteObserver: Observer<LoadState<Unit>>
    private lateinit var viewModel: RemindersViewModel

    @Before
    fun setUp() {
        val scheduler = Schedulers.trampoline()
        val getUseCase = GetRemindersUseCase(reminderDataSource, scheduler, scheduler)
        val deleteUseCase = DeleteRemindersUseCase(reminderDataSource, scheduler, scheduler)
        viewModel = RemindersViewModel(getUseCase, deleteUseCase)
        viewModel.remindersState.observeForever(remindersObserver)
        viewModel.deleteReminderState.observeForever(deleteObserver)
    }

    @Test
    fun loadReminders_success() {
        val reminder = MovieReminder(1, Movie(1, "", "", Date(), 0.0, "", "",0), Date(0))
        `when`(reminderDataSource.getReminders()).thenReturn(Flowable.just(listOf(reminder)))
        viewModel.loadReminders()
        verify(remindersObserver).onChanged(LoadState.Loading)
        verify(remindersObserver).onChanged(LoadState.Data(listOf(reminder)))
    }

    @Test
    fun loadReminders_error() {
        val connectionError = IOException("Connection error")
        `when`(reminderDataSource.getReminders()).thenReturn(Flowable.error(connectionError))
        viewModel.loadReminders()
        verify(remindersObserver).onChanged(LoadState.Loading)
        verify(remindersObserver).onChanged(LoadState.Error(connectionError))
    }

    @Test
    fun deleteReminder_success() {
        val request = DeleteRemindersRequest(listOf(1))
        `when`(reminderDataSource.deleteReminders(request)).thenReturn(Completable.complete())
        viewModel.deleteReminder(1)
        verify(deleteObserver).onChanged(LoadState.Loading)
        verify(deleteObserver).onChanged(LoadState.Data(Unit))
    }
    @Test
    fun deleteReminder_error() {
        val connectionError = IOException("Connection error")
        val request = DeleteRemindersRequest(listOf(1))
        `when`(reminderDataSource.deleteReminders(request)).thenReturn(Completable.error(connectionError))
        viewModel.deleteReminder(1)
        verify(deleteObserver).onChanged(LoadState.Loading)
        verify(deleteObserver).onChanged(LoadState.Error(connectionError))
    }
}