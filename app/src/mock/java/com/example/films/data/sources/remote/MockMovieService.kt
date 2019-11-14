package com.example.films.data.sources.remote

import com.example.films.TestData
import com.example.films.data.models.Movie
import io.reactivex.Single
import java.util.*

class MockMovieService(private val movies: TestData.Movies) : MovieService {

    override fun search(query: String): Single<List<Movie>> {
        val results = mutableListOf<Movie>()
        for (movie in movies.all()) {
            if (movie.title.contains(query, true)) {
                results.add(movie)
            }
        }
        return Single.just(results)
    }

    override fun newReleases(): Single<List<Movie>> {
        return Single.just(movies.getNewReleases())
    }

    override fun upcomingMovies(): Single<List<Movie>> {
        return Single.just(movies.getUpcoming())
    }
}
