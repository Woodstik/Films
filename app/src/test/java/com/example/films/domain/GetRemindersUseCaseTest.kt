package com.example.films.domain

import com.example.films.data.models.Movie
import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.sources.ReminderDataSource
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class GetRemindersUseCaseTest {

    @Mock
    private lateinit var reminderDataSource: ReminderDataSource
    private val scheduler = Schedulers.trampoline()

    @Before
    fun setUp() {
    }

    @Test
    fun getReminders_success(){
        val reminder = MovieReminder(1L, Movie(1, "", "", Date(), 0.0, "", ""), Date())
        `when`(reminderDataSource.getReminders()).thenReturn(Flowable.just(listOf(reminder)))
        val testSubscriber = GetRemindersUseCase(reminderDataSource, scheduler, scheduler)
            .execute()
            .test()
        testSubscriber.assertValue { it == listOf(reminder) }
        testSubscriber.assertComplete()
    }

    @Test
    fun getReminders_emptyList(){
        `when`(reminderDataSource.getReminders()).thenReturn(Flowable.just(emptyList()))
        val testSubscriber = GetRemindersUseCase(reminderDataSource, scheduler, scheduler)
            .execute()
            .test()
        testSubscriber.assertValue { it.isEmpty() }
        testSubscriber.assertComplete()
    }

    @Test
    fun getReminders_multipleValues(){
        val subject = BehaviorSubject.create<List<MovieReminder>>()
        `when`(reminderDataSource.getReminders())
            .thenReturn(subject.toFlowable(BackpressureStrategy.DROP))
        val testSubscriber = GetRemindersUseCase(reminderDataSource, scheduler, scheduler)
            .execute()
            .test()
        testSubscriber.assertEmpty()
        testSubscriber.assertNotComplete()
        subject.onNext(emptyList())
        testSubscriber.assertValueCount(1)
        testSubscriber.assertNotComplete()
        subject.onNext(emptyList())
        testSubscriber.assertValueCount(2)
        testSubscriber.assertNotComplete()
    }

    @Test
    fun getReminders_error(){
        `when`(reminderDataSource.getReminders())
            .thenReturn(Flowable.error(IOException("Connection error")))
        val testSubscriber = GetRemindersUseCase(reminderDataSource, scheduler, scheduler)
            .execute()
            .test()
        testSubscriber.assertError { it is IOException }
    }
}