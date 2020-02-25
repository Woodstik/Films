package com.example.films.domain

import com.example.films.data.models.Movie
import com.example.films.data.models.Post
import com.example.films.data.sources.MovieDataSource
import com.example.films.data.sources.PostDataSource
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class GetHomeUseCaseTest {

    @Mock
    private lateinit var postDataSource: PostDataSource
    @Mock
    private lateinit var movieDataSource: MovieDataSource
    private val scheduler = Schedulers.trampoline()

    @Before
    fun setUp() {
    }

    @Test
    fun getHome_success() {
        val newMovie = Movie(1, "", "", Date(), 0.0, "", "",0)
        `when`(movieDataSource.getNewReleases()).thenReturn(Flowable.just(listOf(newMovie)))
        val upcomingMovie = Movie(2, "", "", Date(), 0.0, "", "",0)
        `when`(movieDataSource.getUpcomingMovies()).thenReturn(Flowable.just(listOf(upcomingMovie)))
        val post = Post("", "", Date(), "", "", 1L, emptyList())
        `when`(postDataSource.getPosts()).thenReturn(Flowable.just(listOf(post)))
        val getHomeUseCase = GetHomeUseCase(movieDataSource, postDataSource, scheduler, scheduler)
        val testScheduler = getHomeUseCase.execute()
            .test()
        testScheduler.assertValue {
            it.upcomingMovies == listOf(upcomingMovie)
                    && it.newReleases == listOf(newMovie)
                    && it.posts == listOf(post)
        }
        testScheduler.assertComplete()
    }

    @Test
    fun getHome_emptyLists() {
        `when`(movieDataSource.getNewReleases()).thenReturn(Flowable.just(emptyList()))
        `when`(movieDataSource.getUpcomingMovies()).thenReturn(Flowable.just(emptyList()))
        `when`(postDataSource.getPosts()).thenReturn(Flowable.just(emptyList()))
        val getHomeUseCase = GetHomeUseCase(movieDataSource, postDataSource, scheduler, scheduler)
        val testScheduler = getHomeUseCase.execute()
            .test()
        testScheduler.assertValue { it.newReleases.isEmpty() && it.posts.isEmpty() && it.upcomingMovies.isEmpty() }
        testScheduler.assertComplete()
    }

    @Test
    fun getHome_newReleaseError() {
        `when`(movieDataSource.getNewReleases()).thenReturn(Flowable.error(IOException("Connection error")))
        `when`(movieDataSource.getUpcomingMovies()).thenReturn(Flowable.just(emptyList()))
        `when`(postDataSource.getPosts()).thenReturn(Flowable.just(emptyList()))
        val getHomeUseCase = GetHomeUseCase(movieDataSource, postDataSource, scheduler, scheduler)
        val testScheduler = getHomeUseCase.execute()
            .test()
        testScheduler.assertError { it is IOException }
    }

    @Test
    fun getHome_upcomingError() {
        `when`(movieDataSource.getNewReleases()).thenReturn(Flowable.just(emptyList()))
        `when`(movieDataSource.getUpcomingMovies()).thenReturn(Flowable.error(IOException("Connection error")))
        `when`(postDataSource.getPosts()).thenReturn(Flowable.just(emptyList()))
        val getHomeUseCase = GetHomeUseCase(movieDataSource, postDataSource, scheduler, scheduler)
        val testScheduler = getHomeUseCase.execute()
            .test()
        testScheduler.assertError { it is IOException }
    }

    @Test
    fun getHome_postError() {
        `when`(movieDataSource.getNewReleases()).thenReturn(Flowable.just(emptyList()))
        `when`(movieDataSource.getUpcomingMovies()).thenReturn(Flowable.just(emptyList()))
        `when`(postDataSource.getPosts()).thenReturn(Flowable.error(IOException("Connection error")))
        val getHomeUseCase = GetHomeUseCase(movieDataSource, postDataSource, scheduler, scheduler)
        val testScheduler = getHomeUseCase.execute()
            .test()
        testScheduler.assertError { it is IOException }
    }
}
