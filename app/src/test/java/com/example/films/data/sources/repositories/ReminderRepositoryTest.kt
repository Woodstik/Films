package com.example.films.data.sources.repositories

import com.example.films.data.models.Movie
import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.CreateReminderRequest
import com.example.films.data.requests.DeleteRemindersRequest
import com.example.films.data.sources.ReminderDataSource
import com.example.films.data.sources.remote.ReminderService
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ReminderRepositoryTest {

    @Mock
    private lateinit var reminderService: ReminderService
    private lateinit var reminderDataSource: ReminderDataSource

    @Before
    fun onSetup(){
        reminderDataSource = ReminderRepository(reminderService)
    }

    @Test
    fun getNextReminder_noReminders() {
        `when`(reminderService.getReminders()).thenReturn(Single.just(emptyList()))
        val getNextSubscriber = reminderDataSource.getNextReminder().test()
        getNextSubscriber.assertValueCount(1)
    }

    @Test
    fun getNextReminder_success() {
        val reminder = MovieReminder(1, Movie(1, "", "", Date(), 0.0, "", ""), Date())
        val reminder2 = MovieReminder(2, Movie(2, "", "", Date(), 0.0, "", ""), Date())
        `when`(reminderService.getReminders()).thenReturn(Single.just(listOf(reminder, reminder2)))
        val testSubscriber = reminderDataSource.getNextReminder().test()
        testSubscriber.assertValueCount(1)
    }

    @Test
    fun getNextReminder_serviceError() {
        `when`(reminderService.getReminders()).thenReturn(Single.error(IOException("Connection error")))
        val testSubscriber = reminderDataSource.getNextReminder().test()
        testSubscriber.assertError { it is IOException }
    }

    @Test
    fun getReminders_emptyList() {
        `when`(reminderService.getReminders()).thenReturn(Single.just(emptyList()))
        val testSubscriber = reminderDataSource.getReminders().test()
        testSubscriber.assertValue { it.isEmpty() }
        testSubscriber.assertValueCount(1)
    }

    @Test
    fun getReminders_successValue() {
        val reminder = MovieReminder(1, Movie(1, "", "", Date(), 0.0, "", ""), Date())
        `when`(reminderService.getReminders()).thenReturn(Single.just(listOf(reminder)))
        val testSubscriber = reminderDataSource.getReminders().test()
        testSubscriber.assertValue { it.contains(reminder) }
        testSubscriber.assertValueCount(1)
    }

    @Test
    fun getReminders_noForceFetch() {
        `when`(reminderService.getReminders()).thenReturn(Single.just(emptyList()))
        var testSubscriber = reminderDataSource.getReminders().test()
        testSubscriber.assertValue { it.isEmpty() }
        testSubscriber.assertValueCount(1)
        val reminder = MovieReminder(1, Movie(1, "", "", Date(), 0.0, "", ""), Date())
        `when`(reminderService.getReminders()).thenReturn(Single.just(listOf(reminder)))
        testSubscriber = reminderDataSource.getReminders(forceRefresh = false).test()
        testSubscriber.assertValue { it.isEmpty() }
        testSubscriber.assertValueCount(1)
        testSubscriber = reminderDataSource.getReminders().test()
        testSubscriber.assertValue { it.contains(reminder) }
        testSubscriber.assertValueCount(1)
    }

    @Test
    fun getReminders_serviceError() {
        `when`(reminderService.getReminders()).thenReturn(Single.error(IOException("Connection error")))
        val testSubscriber = reminderDataSource.getReminders().test()
        testSubscriber.assertError { it is IOException }
    }

    @Test
    fun getTodayReminders_emptyList() {
        `when`(reminderService.getTodayReminders()).thenReturn(Single.just(emptyList()))
        val testSubscriber = reminderDataSource.getTodayReminders().test()
        testSubscriber.assertValue { it.isEmpty() }
        testSubscriber.assertValueCount(1)
    }

    @Test
    fun getTodayReminders_successValue() {
        val reminder = MovieReminder(1, Movie(1, "", "", Date(), 0.0, "", ""), Date())
        `when`(reminderService.getTodayReminders()).thenReturn(Single.just(listOf(reminder)))
        val testSubscriber = reminderDataSource.getTodayReminders().test()
        testSubscriber.assertValue { it.contains(reminder) }
        testSubscriber.assertValueCount(1)
        testSubscriber.assertComplete()
    }

    @Test
    fun getTodayReminders_serviceError() {
        `when`(reminderService.getTodayReminders()).thenReturn(Single.error(IOException("Connection error")))
        val testSubscriber = reminderDataSource.getTodayReminders().test()
        testSubscriber.assertError { it is IOException }
    }

    @Test
    fun createReminder_success() {
        val reminder = MovieReminder(1, Movie(1, "", "", Date(), 0.0, "", ""), Date())
        `when`(reminderService.getReminders()).thenReturn(Single.just(listOf(reminder)))
        val request = CreateReminderRequest(1)
        `when`(reminderService.createReminder(request)).thenReturn(Single.just(2))
        val getSubscriber = reminderDataSource.getReminders().test()
        val addSubscriber = reminderDataSource.createReminder(request).test()
        addSubscriber.assertComplete()
        getSubscriber.assertValueCount(2)
    }

    @Test
    fun createReminder_serviceError() {
        `when`(reminderService.getReminders()).thenReturn(Single.just(emptyList()))
        val request = CreateReminderRequest(1)
        `when`(reminderService.createReminder(request)).thenReturn(Single.error(IOException("Connection error")))
        val getSubscriber = reminderDataSource.getReminders().test()
        val addSubscriber = reminderDataSource.createReminder(request).test()
        addSubscriber.assertError{ it is IOException}
        getSubscriber.assertValueCount(1)
    }

    @Test
    fun createReminder_getListError() {
        `when`(reminderService.getReminders()).thenReturn(Single.error(IOException("Connection error")))
        val request = CreateReminderRequest(1)
        `when`(reminderService.createReminder(request)).thenReturn(Single.just(1))
        val getSubscriber = reminderDataSource.getReminders().test()
        val addSubscriber = reminderDataSource.createReminder(request).test()
        addSubscriber.assertError{it is IOException}
        getSubscriber.assertError{it is IOException}
    }

    @Test
    fun deleteReminder_success() {
        val reminder = MovieReminder(1, Movie(1, "", "", Date(), 0.0, "", ""), Date())
        `when`(reminderService.getReminders()).thenReturn(Single.just(listOf(reminder)))
        val request = DeleteRemindersRequest(listOf(1))
        `when`(reminderService.deleteReminders(request)).thenReturn(Completable.complete())
        val getSubscriber = reminderDataSource.getReminders().test()
        val addSubscriber = reminderDataSource.deleteReminders(request).test()
        addSubscriber.assertComplete()
        getSubscriber.assertValueCount(2)
    }

    @Test
    fun deleteReminder_serviceError() {
        `when`(reminderService.getReminders()).thenReturn(Single.just(emptyList()))
        val request = DeleteRemindersRequest(listOf(1))
        `when`(reminderService.deleteReminders(request)).thenReturn(Completable.error(IOException("Connection error")))
        val getSubscriber = reminderDataSource.getReminders().test()
        val addSubscriber = reminderDataSource.deleteReminders(request).test()
        addSubscriber.assertError{ it is IOException}
        getSubscriber.assertValueCount(1)
    }

    @Test
    fun deleteReminder_getListError() {
        `when`(reminderService.getReminders()).thenReturn(Single.error(IOException("Connection error")))
        val request = DeleteRemindersRequest(listOf(1))
        `when`(reminderService.deleteReminders(request)).thenReturn(Completable.complete())
        val getSubscriber = reminderDataSource.getReminders().test()
        val addSubscriber = reminderDataSource.deleteReminders(request).test()
        addSubscriber.assertError{it is IOException}
        getSubscriber.assertError{it is IOException}
    }
}