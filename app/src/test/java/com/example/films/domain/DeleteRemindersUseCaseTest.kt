package com.example.films.domain

import com.example.films.data.requests.DeleteRemindersRequest
import com.example.films.data.sources.ReminderDataSource
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class DeleteRemindersUseCaseTest {

    @Mock
    private lateinit var reminderDataSource: ReminderDataSource
    private val scheduler = Schedulers.trampoline()

    @Before
    fun setUp() {
    }

    @Test
    fun deleteReminders_success() {
        val request = DeleteRemindersRequest(listOf(1))
        `when`(reminderDataSource.deleteReminders(request)).thenReturn(Completable.complete())
        val testSubscriber = DeleteRemindersUseCase(reminderDataSource, scheduler, scheduler)
            .execute(request)
            .test()
        testSubscriber.assertValueCount(1)
        testSubscriber.assertComplete()
    }

    @Test
    fun deleteReminders_error() {
        val request = DeleteRemindersRequest(listOf(1))
        `when`(reminderDataSource.deleteReminders(request))
            .thenReturn(Completable.error(IOException("Connection error")))
        val testSubscriber = DeleteRemindersUseCase(reminderDataSource, scheduler, scheduler)
            .execute(request)
            .test()
        testSubscriber.assertError { it is IOException }
    }

    @Test(expected = KotlinNullPointerException::class)
    fun deleteReminders_noRequest() {
        DeleteRemindersUseCase(reminderDataSource, scheduler, scheduler)
            .execute()
            .test()
    }
}