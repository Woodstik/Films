package com.example.films.data.sources.remote

import com.example.films.data.models.Movie
import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import io.reactivex.Single
import java.util.*

class MockMovieListsService : MovieListsService {

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
                        "https://image.tmdb.org/t/p/w780/phxiKFDvPeQj4AbkvJLmuZEieDU.jpg"
                    ),
                    Date()
                )
            )
        )
    }

    override fun getMovieLists(): Single<List<MovieList>> {
        val movieLists = mutableListOf<MovieList>()
        movieLists.add(MovieList(1, "Watchlist", "Movies You want to watch", Date(), emptyList()))
        movieLists.add(MovieList(2, "Collection", "I have these movies", Date(), emptyList()))
        return Single.just(movieLists)
    }
}
