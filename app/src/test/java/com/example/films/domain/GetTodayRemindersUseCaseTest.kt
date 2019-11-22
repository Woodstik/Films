package com.example.films.domain

import com.example.films.data.models.Movie
import com.example.films.data.models.MovieReminder
import com.example.films.data.sources.ReminderDataSource
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class GetTodayRemindersUseCaseTest {

    @Mock
    private lateinit var reminderDataSource: ReminderDataSource
    private val scheduler = Schedulers.trampoline()

    @Before
    fun setUp() {
    }

    @Test
    fun getTodayReminders_success(){
        val reminder = MovieReminder(1L, Movie(1, "", "", Date(), 0.0, "", ""), Date())
        `when`(reminderDataSource.getTodayReminders()).thenReturn(Flowable.just(listOf(reminder)))
        val testSubscriber = GetTodayRemindersUseCase(reminderDataSource, scheduler, scheduler)
            .execute()
            .test()
        testSubscriber.assertValue { it == listOf(reminder) }
        testSubscriber.assertComplete()
    }

    @Test
    fun getTodayReminders_emptyList(){
        `when`(reminderDataSource.getTodayReminders()).thenReturn(Flowable.just(emptyList()))
        val testSubscriber = GetTodayRemindersUseCase(reminderDataSource, scheduler, scheduler)
            .execute()
            .test()
        testSubscriber.assertValue { it.isEmpty() }
        testSubscriber.assertComplete()
    }

    @Test
    fun getTodayReminders_error(){
        `when`(reminderDataSource.getTodayReminders()).thenReturn(Flowable.error(IOException("Connection error")))
        val testSubscriber = GetTodayRemindersUseCase(reminderDataSource, scheduler, scheduler)
            .execute()
            .test()
        testSubscriber.assertError { it is IOException }
    }
}