package com.example.films.domain

import com.example.films.data.models.HomeMovies
import com.example.films.data.models.Movie
import com.example.films.data.models.Post
import com.example.films.data.sources.MovieDataSource
import com.example.films.data.sources.PostDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.functions.Function3

class GetHomeUseCase(
    private val movieDataSource: MovieDataSource,
    private val postDataSource: PostDataSource,
    schedulerIO: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<Void, HomeMovies>(schedulerIO, schedulerMainThread) {

    override fun onCreate(parameter: Void?): Flowable<HomeMovies> {
        return Flowable.zip(
            movieDataSource.getNewReleases(),
            movieDataSource.getUpcomingMovies(),
            postDataSource.getPosts(),
            Function3 { newReleases: List<Movie>, upcomingMovies: List<Movie>, posts: List<Post> ->
                HomeMovies(
                    newReleases,
                    upcomingMovies,
                    posts
                )
            }
        )
    }

}
