package com.example.films.data.sources.repositories

import com.example.films.data.models.MovieList
import com.example.films.data.requests.AddMovieToListRequest
import com.example.films.data.requests.CreateMovieListRequest
import com.example.films.data.sources.MovieListDataSource
import com.example.films.data.sources.remote.MovieListsService
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MovieListRepositoryTest {

    @Mock
    private lateinit var movieListsService: MovieListsService
    private lateinit var movieListDataSource: MovieListDataSource

    @Before
    fun onSetup() {
        movieListDataSource = MovieListRepository(movieListsService)
    }

    @Test
    fun getMovieLists_emptyList() {
        `when`(movieListsService.getMovieLists()).thenReturn(Single.just(emptyList()))
        val testSubscriber = movieListDataSource.getMovieLists().test()
        testSubscriber.assertValue { it.isEmpty() }
        testSubscriber.assertValueCount(1)
    }

    @Test
    fun getMovieLists_successValue() {
        val movieList = MovieList(1, "", Date(), mutableListOf(), "")
        `when`(movieListsService.getMovieLists()).thenReturn(Single.just(listOf(movieList)))
        val testSubscriber = movieListDataSource.getMovieLists().test()
        testSubscriber.assertValue { it.contains(movieList) }
        testSubscriber.assertValueCount(1)
    }

    @Test
    fun getMovieLists_noForceFetch() {
        `when`(movieListsService.getMovieLists()).thenReturn(Single.just(emptyList()))
        var testSubscriber = movieListDataSource.getMovieLists().test()
        testSubscriber.assertValue { it.isEmpty() }
        testSubscriber.assertValueCount(1)
        val movieList = MovieList(1, "", Date(), mutableListOf(), "")
        `when`(movieListsService.getMovieLists()).thenReturn(Single.just(listOf(movieList)))
        testSubscriber = movieListDataSource.getMovieLists(forceRefresh = false).test()
        testSubscriber.assertValue { it.isEmpty() }
        testSubscriber.assertValueCount(1)
        testSubscriber = movieListDataSource.getMovieLists().test()
        testSubscriber.assertValue { it.contains(movieList) }
        testSubscriber.assertValueCount(1)
    }

    @Test
    fun getMovieLists_serviceError() {
        `when`(movieListsService.getMovieLists()).thenReturn(Single.error(IOException("Connection error")))
        val testSubscriber = movieListDataSource.getMovieLists().test()
        testSubscriber.assertError { it is IOException }
    }

    @Test
    fun addMovieToList_success() {
        val movieList = MovieList(1, "", Date(), mutableListOf(), "")
        `when`(movieListsService.getMovieLists()).thenReturn(Single.just(listOf(movieList)))
        val request = AddMovieToListRequest(1, 1)
        `when`(movieListsService.addMovieToList(request)).thenReturn(Completable.complete())
        val getSubscriber = movieListDataSource.getMovieLists().test()
        val addSubscriber = movieListDataSource.addMovieToList(request).test()
        addSubscriber.assertNoValues()
        addSubscriber.assertComplete()
        getSubscriber.assertValueCount(2)
    }

    @Test
    fun addMovieToList_serviceError() {
        `when`(movieListsService.getMovieLists()).thenReturn(Single.just(emptyList()))
        val request = AddMovieToListRequest(1, 1)
        `when`(movieListsService.addMovieToList(request)).thenReturn(Completable.error(IOException("Connection error")))
        val getSubscriber = movieListDataSource.getMovieLists().test()
        val addSubscriber = movieListDataSource.addMovieToList(request).test()
        addSubscriber.assertError{ it is IOException}
        getSubscriber.assertValueCount(1)
    }

    @Test
    fun addMovieToList_getListError() {
        `when`(movieListsService.getMovieLists()).thenReturn(Single.error(IOException("Connection error")))
        val request = AddMovieToListRequest(1, 1)
        `when`(movieListsService.addMovieToList(request)).thenReturn(Completable.complete())
        val getSubscriber = movieListDataSource.getMovieLists().test()
        val addSubscriber = movieListDataSource.addMovieToList(request).test()
        addSubscriber.assertError{it is IOException}
        getSubscriber.assertError{it is IOException}
    }

    @Test
    fun createList_success() {
        val movieList = MovieList(1, "", Date(), mutableListOf(), "")
        `when`(movieListsService.getMovieLists()).thenReturn(Single.just(listOf(movieList)))
        val request = CreateMovieListRequest("List")
        `when`(movieListsService.createMovieList(request)).thenReturn(Single.just(2))
        val getSubscriber = movieListDataSource.getMovieLists().test()
        val addSubscriber = movieListDataSource.createList(request).test()
        addSubscriber.assertValue { it == 2.toLong() }
        addSubscriber.assertComplete()
        getSubscriber.assertValueCount(2)
    }

    @Test
    fun createList_serviceError() {
        `when`(movieListsService.getMovieLists()).thenReturn(Single.just(emptyList()))
        val request = CreateMovieListRequest("List")
        `when`(movieListsService.createMovieList(request)).thenReturn(Single.error(IOException("Connection error")))
        val getSubscriber = movieListDataSource.getMovieLists().test()
        val addSubscriber = movieListDataSource.createList(request).test()
        addSubscriber.assertError{ it is IOException}
        getSubscriber.assertValueCount(1)
    }

    @Test
    fun createList_getListError() {
        `when`(movieListsService.getMovieLists()).thenReturn(Single.error(IOException("Connection error")))
        val request = CreateMovieListRequest("List")
        `when`(movieListsService.createMovieList(request)).thenReturn(Single.just(2))
        val getSubscriber = movieListDataSource.getMovieLists().test()
        val addSubscriber = movieListDataSource.createList(request).test()
        addSubscriber.assertError{it is IOException}
        getSubscriber.assertError{it is IOException}
    }
}
