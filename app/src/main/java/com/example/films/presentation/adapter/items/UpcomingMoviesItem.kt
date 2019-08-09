package com.example.films.presentation.adapter.items

import com.example.films.R
import com.example.films.data.models.Movie

class UpcomingMoviesItem(val upcomingMovies: List<Movie>) : AdapterItem {

    override fun getViewType(): Int = R.layout.row_upcoming_movies

    override fun getSpan(): Int = R.integer.home_max_span

    override fun isSameItem(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem)
    }

    override fun isSameContent(otherItem: AdapterItem): Boolean {
        return upcomingMovies == (otherItem as UpcomingMoviesItem).upcomingMovies
    }
}
