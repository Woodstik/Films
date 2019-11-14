package com.example.films.data.sources.repositories

import com.example.films.TestData
import com.example.films.data.sources.remote.MockMovieService
import org.junit.Test

class MovieRepositoryTest {

    private val movieRepository = MovieRepository(MockMovieService(TestData.Movies))

    @Test
    fun getNewReleases() {
        val testSubscriber = movieRepository.getNewReleases().test()
        testSubscriber.assertComplete()
    }

    @Test
    fun getUpcomingMovies() {
        val testSubscriber = movieRepository.getUpcomingMovies().test()
        testSubscriber.assertComplete()
    }
}
