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
                        "https://image.tmdb.org/t/p/w780/phxiKFDvPeQj4AbkvJLmuZEieDU.jpg",
                        trailerUrl = "https://youtu.be/QWbMckU3AOQ"
                    ),
                    Date()
                )
            )
        )
    }

    override fun getMovieLists(): Single<List<MovieList>> {
        val movieLists = mutableListOf<MovieList>()
        movieLists.add(MovieList(1, "Watchlist", Date(), emptyList(), "#F44336"))
        movieLists.add(MovieList(2, "Collection", Date(), emptyList(), "#E91E63"))
        movieLists.add(MovieList(3, "Animated Favorites", Date(), emptyList(), "#9C27B0"))
        movieLists.add(MovieList(4, "List #4", Date(), emptyList(), "#673AB7"))
        movieLists.add(MovieList(5, "List #5", Date(), emptyList(), "#3F51B5"))
        movieLists.add(MovieList(6, "List #6", Date(), emptyList(), "#2196F3"))
        movieLists.add(MovieList(7, "List #7", Date(), emptyList(), "#03A9F4"))
        movieLists.add(MovieList(8, "List #8", Date(), emptyList(), "#00BCD4"))
        movieLists.add(MovieList(9, "List #9", Date(), emptyList(), "#009688"))
        movieLists.add(MovieList(10, "List #10", Date(), emptyList(), "#4CAF50"))
        movieLists.add(MovieList(11, "List #11", Date(), emptyList(), "#8BC34A"))
        return Single.just(movieLists)
    }
}
