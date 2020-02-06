package com.example.films.presentation.adapter.items

import com.example.films.R
import com.example.films.data.models.Movie

class MovieListMovieItem(val movie: Movie) : AdapterItem {

    override fun getViewType(): Int = R.layout.row_movie_list_movie

    override fun isSameItem(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem) && movie.id == (otherItem as MovieListMovieItem).movie.id
    }

    override fun isSameContent(otherItem: AdapterItem): Boolean {
        return movie == (otherItem as MovieListMovieItem).movie
    }
}
