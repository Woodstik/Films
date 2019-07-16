package com.example.films.domain

import com.example.films.data.models.HomeMovies
import com.example.films.data.models.Movie
import com.example.films.data.sources.MovieDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.functions.Function3

class GetHomeUseCase(
    private val movieDataSource: MovieDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<Void, HomeMovies>(schedulerIO, schedulerMainThread) {

    override fun onCreate(parameter: Void?): Flowable<HomeMovies> {
        return Flowable.zip(
            movieDataSource.getNewReleases(),
            movieDataSource.getUpcomingMovies(),
            movieDataSource.getPopularMovies(),
            Function3 { newReleases: List<Movie>, upcomingMovies: List<Movie>, popularMovies: List<Movie> ->
                HomeMovies(
                    newReleases,
                    upcomingMovies,
                    popularMovies
                )
            }
        )
    }

}
