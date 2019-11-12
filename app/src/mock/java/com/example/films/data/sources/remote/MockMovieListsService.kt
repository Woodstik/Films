package com.example.films.data.sources.remote

import com.example.films.data.models.Movie
import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.CreateMovieListRequest
import io.reactivex.Single
import java.util.*

class MockMovieListsService : MovieListsService {

    private val movieLists = mutableListOf<MovieList>()

    init {
        movieLists.add(MovieList(1, "Watchlist", Date(), emptyList(), "#F44336"))
        movieLists.add(MovieList(2, "Collection", Date(), emptyList(), "#E91E63"))
    }

    override fun createMovieList(request: CreateMovieListRequest): Single<Int> {
        movieLists.add(MovieList((movieLists.size + 1).toLong(), request.title, Date(), emptyList(), request.color))
        return Single.just(movieLists.size)
    }

    override fun getReminders(): Single<List<MovieReminder>> {
        return Single.just(
            listOf(
                MovieReminder(
                    1,
                    Movie(
                        320288,
                        "Dark Phoenix",
                        "The X-Men face their most formidable and powerful foe when one of their own, Jean Grey, starts to spiral out of control. During a rescue mission in outer space, Jean is nearly killed when she's hit by a mysterious cosmic force. Once she returns home, this force not only makes her infinitely more powerful, but far more unstable. The X-Men must now band together to save her soul and battle aliens that want to use Grey's new abilities to rule the galaxy.",
                        Date(),
                        6.0,
                        "https://image.tmdb.org/t/p/w185/kZv92eTc0Gg3mKxqjjDAM73z9cy.jpg",
                        "https://image.tmdb.org/t/p/w780/phxiKFDvPeQj4AbkvJLmuZEieDU.jpg",
                        trailerUrl = "https://youtu.be/QWbMckU3AOQ"
                    ),
                    Date()
                )
            )
        )
    }

    override fun getMovieLists(): Single<List<MovieList>> {
        return Single.just(movieLists)
    }
}
